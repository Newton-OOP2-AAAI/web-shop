package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, String> {
    @Override
    List<Customer> findAll();
}
