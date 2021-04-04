package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository extends CrudRepository<Product, String> {
    @Override
    List<Product> findAll();
}
