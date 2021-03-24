package org.newton.webshop.rest;



import org.newton.webshop.models.entities.Account;
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

    @GetMapping("/accounts/{id}")
    @ResponseBody
    Account one(@RequestParam String id) {
        return accountService.findById(id);
    }

    @PostMapping("/accounts/{customerId}")
    Account addAccount(@PathVariable String customerId, @RequestBody Account newAccount) {
        return accountService.addAccount(customerId, newAccount);
    }

    @DeleteMapping("/accounts/{id}")
    void deleteAccount(@PathVariable String id) {
        accountService.deleteById(id);

    }
}