package org.newton.webshop.rest;

import org.newton.webshop.models.dto.AccountCreationDto;
import org.newton.webshop.models.dto.CustomerUpdateDto;
import org.newton.webshop.models.dto.AccountDetailsDto;
import org.newton.webshop.models.dto.AccountDto;
import org.newton.webshop.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public AccountDetailsDto getAccountById(@RequestParam String id) {
        return accountService.findById(id);
    }

    @PostMapping
    public AccountDto addAccount(@RequestBody AccountCreationDto accountCreationDto) {
        return accountService.addAccount(accountCreationDto);
    }

    @PutMapping
    public CustomerUpdateDto editCustomerByAccountId(@RequestParam String id, @RequestBody CustomerUpdateDto customerUpdateDto) {
        return accountService.editCustomerByAccountId(id, customerUpdateDto);
    }

    @DeleteMapping
    public void deleteAccountById(@RequestParam String id) {
        accountService.deleteAccountById(id);
    }
}
