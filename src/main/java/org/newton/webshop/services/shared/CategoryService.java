package org.newton.webshop.services.shared;

import org.newton.webshop.models.entities.Category;
import org.newton.webshop.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
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
     * Find all entities based on given set of ids.
     *
     * @param categoryIds a set of ids. If the set is null, it will be treated as an empty set. If an element in the set is null it will be ignored.
     * @return a set of categories
     */
    public Set<Category> findById(@Nullable Set<String> categoryIds) {
        //If null or empty set of ids, just return an empty hashset
        if (categoryIds == null || categoryIds.isEmpty()) {
            return new HashSet<>();
        }
        //If set contains elements, filter out null elements and use the remaining ids to fetch entities from the database
        return categoryIds.stream()
                .filter(Objects::nonNull)
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
     * Compares two categories and returns the updated category.
     * If category id has changed, the new category is fetched from the database.
     *
     * @param newCategoryId id of the new category
     * @param oldCategory   old category
     * @return new category
     */
    public Category getNewCategory(@Nullable String newCategoryId, @Nullable Category oldCategory) {
        //If new id is null, no comparison needed. If condition is false, invoking methods on object won't cause NullPointerException
        if (newCategoryId == null) {
            return null;
        }

        //Parameter oldCategory is nullable, so we avoid NullPointerException
        String oldCategoryId = (oldCategory == null) ? null : oldCategory.getId();

        //If the id hasn't changed, return the old object
        if (newCategoryId.equals(oldCategoryId)) {
            return oldCategory;
        }

        //Fetch the new entity only if the id has changed
        return categoryRepository.findById(newCategoryId).orElseThrow(RuntimeException::new);
    }

    /**
     * Manipulates a set of categories by comparing them to a set of category ids.
     * New categories are fetched from the database, old categories which should no longer be in the set are removed and remaining categories stay.
     *
     * @param newCategoryIds ids of categories that the returned set will contain
     * @param categoriesToUpdate set of the old categories. Note that this method manipulates that object and returns it
     * @return set of the categories with the ids provided
     */
    public Set<Category> getNewCategories(Set<String> newCategoryIds, Set<Category> categoriesToUpdate) {
        //Remove categories that should no longer be there
        categoriesToUpdate.removeIf(category -> !newCategoryIds.contains(category.getId()));

        //If the set of new ids is empty, no further changes are needed
        if (newCategoryIds.isEmpty()) {
            return categoriesToUpdate;
        }

        //Generate a list of the remaining category ids
        List<String> oldChildCategoryIds = categoriesToUpdate
                .stream()
                .map(Category::getId)
                .collect(Collectors.toList());
        //Fetch new categories that we didn't have before
        Set<Category> newChildCategories = newCategoryIds
                .stream()
                .filter(categoryId -> !oldChildCategoryIds.contains(categoryId))
                .map(id -> categoryRepository.findById(id).orElseThrow(RuntimeException::new)) //todo Exception: Category not found
                .collect(Collectors.toSet());
        //Add the new categories
        categoriesToUpdate.addAll(newChildCategories);

        //Return the updated set of categories
        return categoriesToUpdate;
    }

    /**
     * Find all categories
     *
     * @return list of all categories
     */
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    /**
     * Delete a category
     *
     * @param id id of category to delete
     */
    public void deleteCategory(String id) {
        Category category = categoryRepository.findById(id).orElseThrow(RuntimeException::new);
        categoryRepository.delete(category);
    }
}
