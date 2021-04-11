package org.newton.webshop.rest;

import org.newton.webshop.models.entities.Role;
import org.newton.webshop.services.shared.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/all")
    public List<Role> all() {
        return roleService.findAll();
    }

    @GetMapping
    @ResponseBody
    public Role one(@RequestParam String id) {
        return roleService.findById(id);
    }

}