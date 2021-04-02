package org.newton.webshop.services;

import org.newton.webshop.models.entities.Category;
import org.newton.webshop.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
     * Create a category
     *
     * @param newCategory
     * @return the created category
     */
    public Category createCategory(Category newCategory) {
        return categoryRepository.save(newCategory);
    }

    /**
     * Update a category
     * todo: Refactor: See if it's possible to move some of the logic to this service layer. At the moment update() is identical to createCategory()
     *
     * @param category
     * @return the updated category
     */
    public Category update(Category category) {
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
        return categoryRepository.findById(id).orElseThrow(RuntimeException::new);
    }


    //old

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category removeCategory(Category delCategory) {
        return categoryRepository.save(delCategory);
    }


}
