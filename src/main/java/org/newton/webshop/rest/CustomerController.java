package org.newton.webshop.rest;

import lombok.Getter;
import lombok.Setter;
import org.newton.webshop.exceptions.CustomerNotFoundException;
import org.newton.webshop.models.Customer;
import org.newton.webshop.repositories.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    private final CustomerRepository repository;

    CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/customers")
    public List<Customer> all() {
        return repository.findAll();
    }

    @PostMapping("/customers")
    Customer newCustomer(@RequestBody Customer newCustomer) {
        return repository.save(newCustomer);
    }

    // Single item

    @GetMapping("/customers/{id}")
    Customer one(@PathVariable String id) {
        return repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @PutMapping("/customers/{id}")
    Customer replaceCustomer(@RequestBody Customer newCustomer, @PathVariable String id) {

        return repository.findById(id)
                .map(customer -> {
                    customer.setFirstname(newCustomer.getFirstname());
                    customer.setLastname(newCustomer.getLastname());
                    customer.setPhone(newCustomer.getPhone());
                    customer.setEmail(newCustomer.getEmail());
                    customer.setStreetname(newCustomer.getStreetname());
                    customer.setStreetnumber(newCustomer.getStreetnumber());
                    customer.setZipCode(newCustomer.getZipCode());
                    customer.setCity(newCustomer.getCity());
                    customer.setCarts(newCustomer.getCarts());
                    return repository.save(customer);
                })
                .orElseGet(() -> {
                    newCustomer.setId(id);
                    return repository.save(newCustomer);
                });
    }

    @DeleteMapping("/customers/{id}")
    void deleteCustomer(@PathVariable String id) {
        repository.deleteById(id);
    }
}
