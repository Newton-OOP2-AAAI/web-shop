package org.newton.webshop.rest;

import org.newton.webshop.models.Product;
import org.newton.webshop.models.entities.Category;
import org.newton.webshop.services.CategoryService;
import org.newton.webshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/products")
    Iterable<Product> all() {
        return productService.findAll();
    }

    @PostMapping()
    Product newProduct(@RequestBody Product newProduct) {
        return productService.addProduct(newProduct);
    }

    @GetMapping("/products/{id}")
    @ResponseBody
    Product one(@PathVariable String id) {
        return productService.findById(id);
    }

    @PostMapping
    public Category addCategory(@RequestBody Category newCategory) {
        return categoryService.addCategory(newCategory);
    }

    @PostMapping
    public Category removeCategory(@RequestBody Category delCategory) {
        return categoryService.removeCategory(delCategory);
    }
}

