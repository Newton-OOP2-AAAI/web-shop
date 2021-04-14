package org.newton.webshop.rest;

import org.newton.webshop.models.dto.creation.EmployeeCreationDto;
import org.newton.webshop.models.dto.creation.RoleCreationDto;
import org.newton.webshop.models.dto.response.EmployeeDto;
import org.newton.webshop.models.dto.response.RoleDto;
import org.newton.webshop.models.dto.update.EmployeeUpdateDto;
import org.newton.webshop.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    /**
     * Create a employee
     *
     * @param creationDto dto
     * @return employee dto
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDto createEmployee(@RequestBody EmployeeCreationDto creationDto) {
        return staffService.createEmployee(creationDto);
    }

    /**
     * Update a employee
     *
     * @param employeeId        id of employee to update
     * @param employeeUpdateDto dto containing fields that will be updated
     * @return employee dto
     */
    @PutMapping(path = "/{employee_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDto updateEmployeeById(@PathVariable(name = "employee_id") String employeeId,
                                          @RequestBody EmployeeUpdateDto employeeUpdateDto) {
        return staffService.updateEmployee(employeeId, employeeUpdateDto);
    }

    /**
     * Find employee by id
     *
     * @param id category id
     * @return employee dto
     */
    @GetMapping(params = {"id"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDto findEmployeeById(@RequestParam String id) {
        return staffService.findById(id);
    }

    /**
     * Find all employees
     *
     * @return employee dto
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeDto> findAllEmployees() {
        return staffService.findAll();
    }

    /**
     * Delete a employee
     *
     * @param employeeId id of employee to delete
     */
    @DeleteMapping(path = "/{employee_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteEmployeeById(@PathVariable(name = "employee_id") String employeeId) {
        staffService.deleteEmployeeById(employeeId);
    }

    /**
     * Find all roles
     *
     * @return role dto
     */
    @GetMapping(path = "/roles")
    public List<RoleDto> findAllRoles() {
        return staffService.findAllRoles();
    }

    /**
     * Create a role
     *
     * @param creationDto dto
     * @return role dto
     */
    @PostMapping(path = "/role", produces = MediaType.APPLICATION_JSON_VALUE)
    public RoleDto createRole(@RequestBody RoleCreationDto creationDto) {
        return staffService.createRole(creationDto);
    }

    /**
     * Update a role
     *
     * @param roleId     id of role to update
     * @param updateRole dto containing fields that will be updated
     * @return role dto
     */
    @PutMapping(path = "/roles/{role_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RoleDto updateRoleById(@PathVariable(name = "role_id") String roleId, @RequestBody RoleCreationDto updateRole) {
        return staffService.updateRole(roleId, updateRole);
    }

    /**
     * Delete a role
     *
     * @param roleId id of role to delete
     */
    @DeleteMapping(path = "/roles/{role_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteRoleById(@PathVariable(name = "role_id") String roleId) {
        staffService.deleteRoleById(roleId);
    }
}
