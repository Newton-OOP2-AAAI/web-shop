package org.newton.webshop.rest;

import org.newton.webshop.exceptions.EmployeeNotFoundException;
import org.newton.webshop.models.Employee;
import org.newton.webshop.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    private final EmployeeRepository repository;

    @Autowired
    EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/employees")
    public List<Employee> all() {
        return repository.findAll();
    }

    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee newEmployee) {
        return repository.save(newEmployee);
    }

    // Single item

    @GetMapping("/employees/{id}")
    Employee one(@PathVariable String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable String id) {

        return repository.findById(id)
                .map(employee -> {
                    employee.setFirstname(newEmployee.getFirstname());
                    employee.setLastname(newEmployee.getLastname());
                    employee.setPhone(newEmployee.getPhone());
                    employee.setEmail(newEmployee.getEmail());
                    employee.setStreetName(newEmployee.getStreetName());
                    employee.setStreetNumber(newEmployee.getStreetNumber());
                    employee.setZipCode(newEmployee.getZipCode());
                    employee.setCity(newEmployee.getCity());
                    employee.setUsername(newEmployee.getUsername());
                    employee.setPassword(newEmployee.getPassword());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                });
    }

    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable String id) {
        repository.deleteById(id);
    }
}