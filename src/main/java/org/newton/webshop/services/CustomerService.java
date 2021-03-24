package org.newton.webshop.services;

import org.newton.webshop.models.Customer;
import org.newton.webshop.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return (List<Customer>) customerRepository.findAll();
    }

    public Customer findById(String id) {
        return customerRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Customer addCustomer(@RequestBody Customer newCustomer) {
        return customerRepository.save(newCustomer);
    }
}