package org.newton.webshop.services;

import org.newton.webshop.models.entities.Product;
import org.newton.webshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    /**
     * Notes:
     *
     * Lower level services that only handle one repository should return entities.
     * AssortmentService should take care of mapping between Entity/DTO
     *
     */


    //TODO commented code should be removed unless methods are needed in Assortment Service
//    public List<Product> findAll() {
//        return (List<Product>) productRepository.findAll();
//    }
//
//    public Product findById(String id) {
//        return productRepository.findById(id).orElseThrow(RuntimeException::new);
//    }
//
//    public Product addProduct(@RequestBody Product newProduct) {
//        return productRepository.save(newProduct);
//    }
//
//    @PostMapping
//    public Category addCategory(@RequestBody Category newCategory) {
//        return categoryService.addCategory(newCategory);
//    }
//
//    @PostMapping
//    public Category removeCategory(@RequestBody Category delCategory) {
//        return categoryService.removeCategory(delCategory);
//    }

    /**
     * Find a set of products.
     * @param productIds a set of product ids
     * @return a set of products, as long as all ids existed in the database
     */
    public Set<Product> findById(Set<String> productIds) {
        return productIds.stream()
                .map(id -> productRepository.findById(id).orElseThrow(RuntimeException::new))
                .collect(Collectors.toSet());
    }
}
