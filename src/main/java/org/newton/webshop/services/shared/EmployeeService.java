package org.newton.webshop.services.shared;

import org.newton.webshop.exceptions.EmployeeNotFoundException;
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

    public Employee findById(String id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }


    public Employee updateEmployee(Employee employee, String employeeId) {
        return employeeRepository.findById(employeeId).map(employeeUpdate -> {
            employeeUpdate.setRole(employee.getRole());
            employeeUpdate.setFirstname(employee.getFirstname());
            employeeUpdate.setLastname(employee.getLastname());
            employeeUpdate.setPhone(employee.getPhone());
            employeeUpdate.setAddress(employee.getAddress());
            return employeeRepository.save(employeeUpdate);
        }).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    public void deleteEmployee(String id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        employeeRepository.delete(employee);
    }
}
