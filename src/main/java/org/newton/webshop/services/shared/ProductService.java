package org.newton.webshop.services.shared;

import org.newton.webshop.exceptions.CategoryNotFoundException;
import org.newton.webshop.models.entities.Product;
import org.newton.webshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
        return (productIds == null || productIds.isEmpty())
                ? new HashSet<>()
                : productIds.stream()
                .map(id -> productRepository.findById(id).orElseThrow(RuntimeException::new))
                .collect(Collectors.toSet());
    }

    /**
     * Manipulates a set of products by comparing them to a set of product ids.
     * New categories are fetched from the database, old products which should no longer be in the set are removed and remaining products stay.
     *
     * @param newProductIds    ids of products that the returned set will contain
     * @param productsToUpdate set of the old products. Note that this method manipulates that object and returns it
     * @return set of the products with the ids provided
     */
    public Set<Product> getNewProducts(Set<String> newProductIds, Set<Product> productsToUpdate) {
        //Remove associations with child categories that shouldn't exist anymore
        productsToUpdate.removeIf(product -> !newProductIds.contains(product.getId()));

        //If the set of new ids is empty, no further changes are needed
        if (!newProductIds.isEmpty()) {
            return productsToUpdate;
        }

        //Generate a list of the remaining product ids
        List<String> oldProducts = productsToUpdate
                .stream()
                .map(Product::getId)
                .collect(Collectors.toList());
        //Fetch new products that we didn't have before
        Set<Product> newProducts = newProductIds
                .stream()
                .filter(productId -> !oldProducts.contains(productId))
                .map(id -> productRepository.findById(id).orElseThrow(RuntimeException::new)) //todo Exception: Product not found
                .collect(Collectors.toSet());
        //Add the new products
        productsToUpdate.addAll(newProducts);

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

    /**
     * Delete a product
     *
     * @param productId id of product to delete
     */
    public void deleteProduct(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(RuntimeException::new); //todo Exception: Product not found
        productRepository.delete(product);
    }
}
