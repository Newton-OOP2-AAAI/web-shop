package org.newton.webshop.rest;


import org.newton.webshop.exceptions.AccountNotFoundException;

import org.newton.webshop.models.entities.Account;

import org.newton.webshop.models.entities.Category;
import org.newton.webshop.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts")
    Iterable<Account> all() {
        return accountService.findAll();
    }

    @GetMapping
    @ResponseBody
    Account one(@RequestParam String id) {
        return accountService.findById(id);
    }

    @PostMapping("/accounts")
    Account addAccount(@RequestBody Account newAccount) {
        return accountService.addAccount(newAccount);
    }

    //funderar på om vi ska ha kvar deleteAccount eller inte
//    @DeleteMapping("/accounts/{id}")
//    void deleteAccount(@PathVariable String id) {
//        accountService.deleteById(id);
//    }
}