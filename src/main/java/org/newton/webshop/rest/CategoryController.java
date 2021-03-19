package org.newton.webshop.rest;

import org.newton.webshop.exceptions.CategoryNotFoundException;
import org.newton.webshop.models.Category;
import org.newton.webshop.repositories.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    private final CategoryRepository repository;

    CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/category")
    List<Category> all() {
        return repository.findAll();
    }

    @PostMapping("/category")
    Category newCategory(@RequestBody Category newCategory) {
        return repository.save(newCategory);
    }

    @GetMapping("/category/{id}")
    Category one(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @PutMapping("/category/{id}")
    Category replaceCategory(@RequestBody Category newCategory, @PathVariable Integer id) {
        return repository.findById(id)
                .map(category -> {
                    category.setName(newCategory.getName());
                    category.setParentCategories(newCategory.getParentCategories());
                    category.setChildCategories(newCategory.getChildCategories());
                    category.setProducts(newCategory.getProducts());
                    return repository.save(category);
                })
                .orElseGet(() -> {
                    newCategory.setId(id);
                    return repository.save(newCategory);
                });
    }

    @DeleteMapping("/category/{id}")
    void deleteCategory(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
