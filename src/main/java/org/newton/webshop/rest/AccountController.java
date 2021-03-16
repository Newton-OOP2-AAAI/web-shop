package org.newton.webshop.rest;


import org.newton.webshop.exceptions.AccountNotFoundException;

import org.newton.webshop.models.Account;
import org.newton.webshop.repositories.AccountRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    private final AccountRepository repository;

    AccountController(AccountRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/accounts")
    public List<Account> all() {
        return repository.findAll();
    }

    @PostMapping("/accounts")
    Account newAccount(@RequestBody Account newAccount) {
        return repository.save(newAccount);
    }

    // Single item

    @GetMapping("/accounts/{id}")
    Account one(@PathVariable String id) {
        return repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    @PutMapping("/accounts/{id}")
    Account replaceAccount(@RequestBody Account newAccount, @PathVariable String id) {

        return repository.findById(id)
                .map(Account -> {
                    Account.setUsername(newAccount.getUsername());
                    Account.setPassword(newAccount.getPassword());
                    Account.setCreateDate(newAccount.getCreateDate());
                    Account.setBirthDate(newAccount.getBirthDate());
                    return repository.save(Account);
                })
                .orElseGet(() -> {
                    newAccount.setId(id);
                    return repository.save(newAccount);
                });
    }

    @DeleteMapping("/accounts/{id}")
    void deleteAccount(@PathVariable String id) {
        repository.deleteById(id);
    }
}