package org.newton.webshop.rest;

import org.newton.webshop.models.dto.creation.AccountCreationDto;
import org.newton.webshop.models.dto.response.AccountDto;
import org.newton.webshop.models.dto.update.CustomerUpdateDto;
import org.newton.webshop.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public AccountDto getAccountById(@RequestParam String id) {
        return accountService.findById(id);
    }

    @PostMapping
    public AccountDto createAccount(@RequestBody AccountCreationDto accountCreationDto) {
        return accountService.addAccount(accountCreationDto);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountDto updateCustomerByAccountId(@PathVariable String id, @RequestBody CustomerUpdateDto customerUpdateDto) {
        return accountService.updateCustomerByAccountId(id, customerUpdateDto);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteById(@PathVariable String id) {
        accountService.deleteById(id);
    }
}
