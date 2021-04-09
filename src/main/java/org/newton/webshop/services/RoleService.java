package org.newton.webshop.services;

import org.newton.webshop.models.entities.Role;
import org.newton.webshop.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return (List<Role>) roleRepository.findAll();
    }

    public Role findById(String id) {
        return roleRepository.findById(id).orElseThrow(RuntimeException::new); //todo exception: Role not found
    }

    public Role addRole(Role newRole) {
        return roleRepository.save(newRole);
    }
}
