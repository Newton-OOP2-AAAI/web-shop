package org.newton.webshop.rest;

import org.newton.webshop.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Deprecated Should be removed, but keeping it for reference until AssortmentController is fully implemented
 * TODO Remove
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Commented out, but kept until class is removed
     */
//    @GetMapping("/all")
//    List<Category> findAll() {
//        return categoryService.findAll();
//    }
//
//    @GetMapping
//    Category findById(@RequestParam String id) {
//        return categoryService.findById(id);
//    }
//
//    @PostMapping
//    public Category addCategory(@RequestBody Category newCategory) {
//        return categoryService.addCategory(newCategory);
//    }
//
//    @DeleteMapping
//    public Category removeCategory(@RequestBody Category delCategory) {
//        return categoryService.removeCategory(delCategory);
//    }
}
