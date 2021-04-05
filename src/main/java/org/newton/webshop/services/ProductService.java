package org.newton.webshop.services;

import org.newton.webshop.models.entities.Product;
import org.newton.webshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Notes:
 * Lower level services that only handle one repository should return entities.
 * AssortmentService should take care of mapping between Entity/DTO
 */
@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findAllByPriceAsc() {
        return productRepository.findAllByOrderByPriceAsc();
    }

    public List<Product> findAllByPriceDesc() {
        return productRepository.findAllByOrderByPriceDesc();
    }

    public List<Product> findAllByCategory() {
        return productRepository.findAllByOrderByCategory();
    }

    public Product findById(String id) {
        return productRepository.findById(id).orElseThrow(RuntimeException::new); //todo Exception: Product not found
    }

    /**
     * Find a set of products.
     *
     * @param productIds a set of product ids
     * @return a set of products, as long as all ids existed in the database
     */
    public Set<Product> findById(Set<String> productIds) {
        return productIds.stream()
                .map(id -> productRepository.findById(id).orElseThrow(RuntimeException::new))
                .collect(Collectors.toSet());
    }

    public Set<Product> getNewProducts(Set<String> newProductIds, Set<Product> productsToUpdate) {
        //Remove associations with child categories that shouldn't exist anymore
        productsToUpdate.removeIf(product -> !newProductIds.contains(product.getId()));

        //Add associations with child categories which didn't exist before
        if (!newProductIds.isEmpty()) {
            var oldProducts = productsToUpdate
                    .stream()
                    .collect(Collectors.toMap(Product::getId, product -> product));
            var newProducts = newProductIds
                    .stream()
                    .filter(productId -> !oldProducts.containsKey(productId))
                    .map(productRepository::findById)
                    .map(product -> product.orElseThrow(RuntimeException::new)) //todo Exception: Product not found
                    .collect(Collectors.toSet());
            productsToUpdate.addAll(newProducts);
        }

        //Return the updated set of products
        return productsToUpdate;
    }

    /**
     * Persists product in database.
     *
     * @param product entity to create or update
     * @return persisted product
     */
    public Product save(Product product) {
        return productRepository.save(product);
    }
}
