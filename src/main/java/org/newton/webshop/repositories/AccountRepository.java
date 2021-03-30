package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, String> {
    @Override
    List<Account> findAll();
}