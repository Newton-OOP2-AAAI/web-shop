package org.newton.webshop.services;


import org.newton.webshop.models.entities.Account;
import org.newton.webshop.repositories.AccountRepository;
import org.newton.webshop.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> findAll() {
        return (List<Account>) accountRepository.findAll();
    }

    public Account findById(String id) {
        return accountRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Account addAccount(String customerId, Account newAccount) {
        return customerRepository.findById(customerId)
                .map(customer -> {
                    newAccount.setCustomer(customer);
                    return accountRepository.save(newAccount);
                }).orElseThrow(RuntimeException::new);
    }

    public void deleteById(String id) {

        accountRepository.deleteById(id);
    }
}
