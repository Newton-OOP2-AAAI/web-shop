package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, String> {
    @Override
    List<Customer> findAll();

    Optional<Customer> findCustomerByAccount_Id(String id);
}
