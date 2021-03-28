package org.newton.webshop.services;

import org.newton.webshop.models.entities.Category;
import org.newton.webshop.models.entities.Product;
import org.newton.webshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    public Product findById(String id) {
        return productRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Product addProduct(@RequestBody Product newProduct) {
        return productRepository.save(newProduct);
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
