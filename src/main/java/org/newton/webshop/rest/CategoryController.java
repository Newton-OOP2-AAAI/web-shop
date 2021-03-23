package org.newton.webshop.rest;

import org.newton.webshop.models.entities.Category;
import org.newton.webshop.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    Iterable<Category> all() {
        return categoryService.findAll();
    }

    @GetMapping
    @ResponseBody
    Category one(@RequestParam String id) {
        return categoryService.findById(id);
    }

    @PostMapping
    public Category addCategory(@RequestBody Category newCategory) {
        return categoryService.addCategory(newCategory);
    }
}
