package org.newton.webshop.rest;

import org.newton.webshop.models.Customer;
import org.newton.webshop.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public Iterable<Customer> all() {
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
