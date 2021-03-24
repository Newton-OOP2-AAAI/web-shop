package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, String> {
}
