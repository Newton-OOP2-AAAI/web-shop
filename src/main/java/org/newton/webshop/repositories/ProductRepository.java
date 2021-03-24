package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, String> {

}
