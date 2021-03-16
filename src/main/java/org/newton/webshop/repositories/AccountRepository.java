package org.newton.webshop.repositories;

import org.newton.webshop.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {

}