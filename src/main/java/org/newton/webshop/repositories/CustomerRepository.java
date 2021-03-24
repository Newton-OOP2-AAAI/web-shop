package org.newton.webshop.repositories;

import org.newton.webshop.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    //public Optional<Customer> getCustomerById(String id);
}
