package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, String> {

    @Override
    List<Order> findAll();
}
