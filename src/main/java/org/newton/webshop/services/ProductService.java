package org.newton.webshop.services;

import org.newton.webshop.models.entities.Product;
import org.newton.webshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> findByName(String name, Pageable pageable) {
        return productRepository.findByNameContaining(name, pageable);
    }

    public Page<Product> getAllProductsByCategoryId(String categoryId, Pageable pageable) {
        return productRepository.getAllProductsByCategoryId(categoryId, pageable);
    }

    public Page<Product> getAllProductsByCategoryName(String name, Pageable pageable) {
        return productRepository.getAllProductsByCategoryName(name, pageable);
    }

    public Product findById(String id) {
        if (id == null) {
            throw new RuntimeException(); //todo Exception: Product not found
        }
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

    public Product updateProduct(String productId, Product product) {
        return productRepository.findById(productId).map(productUpdate -> {
            productUpdate.setName(product.getName());
            productUpdate.setPrice(product.getPrice());
            productUpdate.setCategory(product.getCategory());
            productUpdate.setDescription(product.getDescription());
            productUpdate.setVisible(product.isVisible());
            return productRepository.save(productUpdate);
        }).orElseThrow(RuntimeException::new);//todo Exception: Product not found
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
