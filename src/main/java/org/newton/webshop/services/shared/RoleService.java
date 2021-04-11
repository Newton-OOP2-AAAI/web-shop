package org.newton.webshop.services.shared;

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
        return roleRepository.findAll();
    }

    public Role findById(String id) {
        return roleRepository.findById(id).orElseThrow(RuntimeException::new); //todo exception: Role not found
    }

    public Role createRole(Role newRole) {
        return roleRepository.save(newRole);
    }

    public Role updateRole(Role oldRole, String roleId) {
        return roleRepository.findById(roleId).map(roleUpdate -> {
            roleUpdate.setEmployee(oldRole.getEmployee());
            roleUpdate.setTitle(oldRole.getTitle());
            roleUpdate.setChatbot(oldRole.getChatbot());
            roleUpdate.setCategories(oldRole.getCategories());
            roleUpdate.setProducts(oldRole.getProducts());
            return roleRepository.save(roleUpdate);
        }).orElseThrow(RuntimeException::new); //todo Exception: Role not found
    }

    public void deleteRole(String id) {
        Role role = roleRepository.findById(id).orElseThrow(RuntimeException::new); //todo Exception: Role not found
        roleRepository.delete(role);
    }
}
