package org.newton.webshop.services;

import org.newton.webshop.models.dto.response.CategoryDto;
import org.newton.webshop.models.entities.Category;
import org.newton.webshop.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Create or update a category.
     *
     * @param category
     * @return the persisted category
     */
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * Find products by ids
     *
     * @param categoryIds a set of category ids
     * @return a set of categories, if all ids existed in the database
     */
    public Set<Category> findById(Set<String> categoryIds) {
        return categoryIds.stream()
                .map(id -> categoryRepository.findById(id).orElseThrow(RuntimeException::new))
                .collect(Collectors.toSet());
    }

    /**
     * Find a product by id
     *
     * @param id category id
     * @return category, if the id existed in database
     */
    public Category findById(String id) {
        return categoryRepository.findById(id).orElseThrow(RuntimeException::new); //todo Exception: Category not found
    }

    /**
     * Compares two categories and returns the updated category
     *
     * @param newCategoryId id of the new category Nullable todo: Fråga Thor: Nullable Annotations?
     * @param oldCategory   old category Nullable todo: Nullable: Kolla vad den gör
     * @return new category
     */
    public Category getNewCategory(@Nullable String newCategoryId, @Nullable Category oldCategory) {
        //If category id has changed, fetch the new parent category. Ternary operators to avoid nullpointer exceptions.
        var oldCategoryId = (oldCategory == null)
                ? null
                : oldCategory.getId();
        return (newCategoryId == null)
                ? null
                : (newCategoryId.equals(oldCategoryId))
                ? oldCategory
                : categoryRepository.findById(newCategoryId).orElseThrow(RuntimeException::new);
    }

    public Set<Category> getNewCategories(Set<String> newCategoryIds, Set<Category> categoriesToUpdate) {
        //Remove categories which are not in newCategoryIds
        categoriesToUpdate.removeIf(category -> !newCategoryIds.contains(category.getId()));

        //Add new categories from newCategoryIds
        if (!newCategoryIds.isEmpty()) {
            var oldChildCategories = categoriesToUpdate
                    .stream()
                    .collect(Collectors.toMap(Category::getId, category -> category));
            var newChildCategories = newCategoryIds
                    .stream()
                    .filter(categoryId -> !oldChildCategories.containsKey(categoryId))
                    .map(categoryRepository::findById)
                    .map(category -> category.orElseThrow(RuntimeException::new)) //todo Exception: Category not found
                    .collect(Collectors.toSet());
            categoriesToUpdate.addAll(newChildCategories);
        }

        //Return the updated set of categories
        return categoriesToUpdate;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void deleteCategory(String id) {
        Category category = categoryRepository.findById(id).orElseThrow(RuntimeException::new);
        categoryRepository.delete(category);
    }
}
