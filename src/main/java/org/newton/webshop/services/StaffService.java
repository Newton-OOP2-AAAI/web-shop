package org.newton.webshop.services;

import org.newton.webshop.models.dto.creation.EmployeeCreationDto;
import org.newton.webshop.models.dto.creation.RoleCreationDto;
import org.newton.webshop.models.dto.response.EmployeeDto;
import org.newton.webshop.models.dto.response.RoleDto;
import org.newton.webshop.models.dto.update.EmployeeUpdateDto;
import org.newton.webshop.models.entities.Address;
import org.newton.webshop.models.entities.Employee;
import org.newton.webshop.models.entities.Role;
import org.newton.webshop.repositories.EmployeeRepository;
import org.newton.webshop.services.shared.EmployeeService;
import org.newton.webshop.services.shared.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles requests by StaffController.
 */
@Service
public class StaffService {
    private final EmployeeService employeeService;
    private final RoleService roleService;

    @Autowired
    public StaffService(EmployeeService employeeService, RoleService roleService) {
        this.employeeService = employeeService;
        this.roleService = roleService;

    }

    /**
     * Create employee
     *
     * @param creationDto dto with required fields
     * @return dto, including the id of the new entity
     */
    public EmployeeDto createEmployee(EmployeeCreationDto creationDto) {
        Role role = roleService.findById(creationDto.getRoleId());
        Employee employee = employeeService.createEmployee(toEntity(creationDto, role));
        return toDto(employee);
    }

    /**
     * Update employee
     *
     * @param employeeId        the id of the employee to update
     * @param employeeUpdateDto dto containing all required fields
     * @return dto of the employee resource
     */
    public EmployeeDto updateEmployee(String employeeId, EmployeeUpdateDto employeeUpdateDto) {
        var role = roleService.findById(employeeUpdateDto.getRoleId());
        var employee = toEntity(employeeUpdateDto, role);
        var updatedEmployee = employeeService.updateEmployee(employee, employeeId);
        return toDto(updatedEmployee);
    }

    /**
     * Find employee by id.
     *
     * @param id employee id
     * @return dto containing
     */
    public EmployeeDto findById(String id) {
        Employee employee = employeeService.findById(id);
        return toDto(employee);
    }

    /**
     * Find all employees
     *
     * @return list of employees, each contained in an dto
     */
    public List<EmployeeDto> findAll() {
        return employeeService.findAll()
                .stream()
                .map(StaffService::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Delete employee
     *
     * @param id id of employee
     */
    public void deleteEmployeeById(String id) {
        employeeService.deleteEmployee(id);
    }

    /**
     * Find all roles
     *
     * @return list of roles, each contained in an dto
     */
    public List<RoleDto> findAllRoles() {
        return roleService.findAll()
                .stream()
                .map(StaffService::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Create role
     *
     * @param creationDto dto with required fields
     * @return dto, including the id of the new entity
     */
    public RoleDto createRole(RoleCreationDto creationDto) {
        Role newRole = roleService.createRole(toEntity(creationDto));
        return toDto(newRole);
    }

    /**
     * Update role
     *
     * @param roleId     the id of the role to update
     * @param updateRole dto containing all required fields
     * @return dto of the role resource
     */
    public RoleDto updateRole(String roleId, RoleCreationDto updateRole) {
        Role oldRole = toEntity(updateRole);
        Role updatedRole = roleService.updateRole(oldRole, roleId);
        return toDto(updatedRole);
    }

    /**
     * Delete role
     *
     * @param id id of role
     */
    public void deleteRoleById(String id) {
        roleService.deleteRole(id);
    }

    /**
     * Converts employee to response dto
     *
     * @param employee employee to convert
     * @return response dto
     */
    private static EmployeeDto toDto(Employee employee) {
        return EmployeeDto.builder()
                .Id(employee.getId())
                .role(employee.getRole().getTitle())
                .firstname(employee.getFirstname())
                .lastname(employee.getLastname())
                .phone(employee.getPhone())
                .email(employee.getEmail())
                .streetName(employee.getAddress().getStreetName())
                .streetNumber(employee.getAddress().getStreetNumber())
                .zipCode(employee.getAddress().getZipCode())
                .city(employee.getAddress().getCity())
                .username(employee.getUsername())
                .build();
    }

    /**
     * Converts dto to employee.
     *
     * @param creationDto contains required information to create an employee entity. The role id field references a role that must be supplied as an additional parameter.
     * @param role        an existing role entity
     * @return employee entity
     */
    private static Employee toEntity(EmployeeCreationDto creationDto, Role role) {
        return Employee.builder()
                .role(role)
                .firstname(creationDto.getFirstname())
                .lastname(creationDto.getLastname())
                .phone(creationDto.getPhone())
                .email(creationDto.getEmail())
                .address(Address.builder()
                        .streetName(creationDto.getStreetName())
                        .streetNumber(creationDto.getStreetNumber())
                        .zipCode(creationDto.getZipCode())
                        .city(creationDto.getCity())
                        .build())
                .username(creationDto.getUsername())
                .password(creationDto.getPassword())
                .build();

    }

    private static Employee toEntity(EmployeeUpdateDto employeeUpdateDto, Role role) {
        return Employee.builder()
                .role(role)
                .firstname(employeeUpdateDto.getFirstname())
                .lastname(employeeUpdateDto.getLastname())
                .phone(employeeUpdateDto.getPhone())
                .address(Address.builder()
                        .streetName(employeeUpdateDto.getStreetName())
                        .streetNumber(employeeUpdateDto.getStreetNumber())
                        .zipCode(employeeUpdateDto.getZipCode())
                        .city(employeeUpdateDto.getCity())
                        .build())
                .build();
    }

    /**
     * Converts dto to role.
     *
     * @param creationDto contains required information to create an role entity
     * @return role entity
     */
    private static Role toEntity(RoleCreationDto creationDto) {
        return Role.builder()
                .id(creationDto.getId())
                .employee(creationDto.getEmployee())
                .title(creationDto.getTitle())
                .chatbot(creationDto.getChatbot())
                .categories(creationDto.getCategories())
                .products(creationDto.getProducts())
                .build();
    }

    /**
     * Converts role to response dto
     *
     * @param role role to convert
     * @return response dto
     */
    private static RoleDto toDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .employee(role.getEmployee())
                .title(role.getTitle())
                .chatbot(role.getChatbot())
                .categories(role.getCategories())
                .products(role.getProducts())
                .build();
    }
}
