package org.newton.webshop.rest;

import org.newton.webshop.services.CategoryService;
import org.newton.webshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Deprecated Should be removed, but keeping it for reference until AssortmentController is fully implemented
 * TODO Remove
 */
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

    /**
     * Commented out, but kept until class is removed
     */
//    @GetMapping("/products")
//    List<Product> all() {
//        return productService.findAll();
//    }
//
//    @PostMapping("/newProduct")
//    Product newProduct(@RequestBody Product newProduct) {
//        return productService.addProduct(newProduct);
//    }
//
//    @GetMapping("/products/{id}")
//    @ResponseBody
//    Product one(@PathVariable String id) {
//        return productService.findById(id);
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

