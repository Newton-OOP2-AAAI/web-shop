package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, String> {
    @Override
    List<Role> findAll();
}
