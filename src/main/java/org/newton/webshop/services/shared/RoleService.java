package org.newton.webshop.services.shared;

import org.newton.webshop.exceptions.RoleNotFoundException;
import org.newton.webshop.models.entities.Role;
import org.newton.webshop.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        return roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(id));
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
        }).orElseThrow(() -> new RoleNotFoundException(roleId));
    }

    public void deleteRole(String id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(id));
        roleRepository.delete(role);
    }
}
