package org.newton.webshop.rest;

import org.newton.webshop.exceptions.RoleNotFoundException;
import org.newton.webshop.models.Role;
import org.newton.webshop.repositories.RoleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {
    private final RoleRepository repository;

    RoleController(RoleRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/roles")
    public List<Role> all() {
        return repository.findAll();
    }

    @PostMapping("/roles")
    Role newRole(@RequestBody Role newRole) {
        return repository.save(newRole);
    }

    @GetMapping("/roles/{id}")
    Role one(@PathVariable String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
    }

    @PutMapping("/roles/{id}")
    Role replaceRole(@RequestBody Role newRole, @PathVariable String id) {

        return repository.findById(id)
                .map(role -> {
                    role.setEmployees(newRole.getEmployees());
                    role.setEmployee(newRole.getEmployee());
                    role.setChatbot(newRole.getChatbot());
                    role.setCategories(newRole.getCategories());
                    role.setProducts(newRole.getProducts());
                    return repository.save(role);
                })
                .orElseGet(() -> {
                    newRole.setId(id);
                    return repository.save(newRole);
                });
    }

    @DeleteMapping("/roles/{id}")
    void deleteEmployee(@PathVariable String id) {
        repository.deleteById(id);
    }
}