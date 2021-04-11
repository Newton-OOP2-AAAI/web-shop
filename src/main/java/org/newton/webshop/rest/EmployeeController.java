package org.newton.webshop.rest;

import org.newton.webshop.models.entities.Employee;
import org.newton.webshop.services.shared.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/all")
    public List<Employee> all() {
        return employeeService.findAll();
    }

    @GetMapping
    public Employee one(@RequestParam String id) {
        return employeeService.findById(id);
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee newEmployee) {
        return employeeService.createEmployee(newEmployee);
    }
}