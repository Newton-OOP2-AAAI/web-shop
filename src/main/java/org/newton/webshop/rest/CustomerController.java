package org.newton.webshop.rest;

import org.newton.webshop.models.entities.Customer;
import org.newton.webshop.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public List<Customer> all() {
        return customerService.findAll();
    }

    @GetMapping
    public Customer one(@RequestParam String id) {
        return customerService.findById(id);
    }

    @PostMapping
    public Customer addCustomer(@RequestBody Customer newCustomer) {
        return customerService.addCustomer(newCustomer);
    }
}
