package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Account;
import org.springframework.data.repository.CrudRepository;


public interface AccountRepository extends CrudRepository<Account, String> {


}