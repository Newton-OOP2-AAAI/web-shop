package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Account;
import org.newton.webshop.models.entities.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, String> {
    @Override
    List<Employee> findAll();
}
