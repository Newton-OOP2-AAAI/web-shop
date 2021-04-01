package org.newton.webshop.services;

import org.newton.webshop.models.entities.Category;
import org.newton.webshop.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    public Category createCategory(@RequestBody Category newCategory) {
        return categoryRepository.save(newCategory);
    }

    /**
     * Find products by ids
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



    public Category removeCategory(@RequestBody Category delCategory) {
        return categoryRepository.save(delCategory);
    }

}
