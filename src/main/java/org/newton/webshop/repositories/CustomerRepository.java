package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, String> {

}
