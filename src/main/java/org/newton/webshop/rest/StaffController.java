package org.newton.webshop.rest;

import org.newton.webshop.models.dto.creation.EmployeeCreationDto;
import org.newton.webshop.models.dto.creation.RoleCreationDto;
import org.newton.webshop.models.dto.response.EmployeeDto;
import org.newton.webshop.models.dto.response.RoleDto;
import org.newton.webshop.models.dto.update.EmployeeUpdateDto;
import org.newton.webshop.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/employees")
public class StaffController {
    private final StaffService staffService;

    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @PostMapping
    public EmployeeDto createEmployee(@RequestBody EmployeeCreationDto creationDto) {
        return staffService.createEmployee(creationDto);
    }

    @PutMapping
    public EmployeeDto updateEmployeeById(@RequestParam String id, @RequestBody EmployeeUpdateDto employeeUpdateDto) {
        return staffService.updateEmployee(id, employeeUpdateDto);
    }

    @GetMapping
    public EmployeeDto findEmployeeById(@RequestParam String id) {
        return staffService.findById(id);
    }

    @GetMapping("/all")
    public List<EmployeeDto> findAllEmployees() {
        return staffService.findAll();
    }

    @DeleteMapping
    public void deleteEmployeeById(@RequestParam String employeeId) {
        staffService.deleteEmployeeById(employeeId);
    }

    @GetMapping("/roles")
    public List<RoleDto> findAllRoles() {
        return staffService.findAllRoles();
    }

    @PostMapping("/role/create")
    public RoleDto createRole(@RequestBody RoleCreationDto creationDto) {
        return staffService.createRole(creationDto);
    }

    @PutMapping("/role/{roleId}")
    public RoleDto updateRoleById(@PathVariable String roleId, @RequestBody RoleCreationDto updateRole) {
        return staffService.updateRole(roleId, updateRole);
    }

    @DeleteMapping("/roles/{roleId}")
    public void deleteRoleById(@PathVariable String roleId) {
        staffService.deleteRoleById(roleId);
    }
}
