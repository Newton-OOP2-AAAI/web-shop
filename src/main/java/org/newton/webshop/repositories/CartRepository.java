package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartRepository extends CrudRepository<Cart, String> {
    @Override
    List<Cart> findAll();

}
