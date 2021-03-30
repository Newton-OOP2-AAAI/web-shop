package org.newton.webshop.services;

import org.newton.webshop.models.entities.Employee;
import org.newton.webshop.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;


    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    public Employee findById(String id) throws RuntimeException {
        return employeeRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Employee> findAll() {
        return (List<Employee>) employeeRepository.findAll();
    }


}
