package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, String> {
    @Override
    List<Product> findAll();

    //Sort by price ascending:
    List<Product> findAllByOrderByPriceAsc();

    //Sort by price descending
    List<Product> findAllByOrderByPriceDesc();

    //Sort by category
    List<Product> findAllByOrderByCategory();
}
