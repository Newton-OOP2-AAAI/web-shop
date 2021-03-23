package org.newton.webshop.services;

import org.newton.webshop.models.entities.Account;
import org.newton.webshop.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@Component
public class AccountService {
    private final AccountRepository accountRepository;

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

    public Account addAccount(@RequestBody Account newAccount) {
        return accountRepository.save(newAccount);
    }

}
