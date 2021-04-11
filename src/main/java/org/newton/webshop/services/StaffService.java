package org.newton.webshop.services;

import org.newton.webshop.models.dto.creation.EmployeeCreationDto;
import org.newton.webshop.models.dto.response.EmployeeDto;
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
    private final EmployeeRepository employeeRepository;

    @Autowired
    public StaffService(EmployeeService employeeService, RoleService roleService, EmployeeRepository employeeRepository) {
        this.employeeService = employeeService;
        this.roleService = roleService;
        this.employeeRepository = employeeRepository;
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
        return toDto(employeeRepository.findById(id).orElseThrow(RuntimeException::new)); //TODO Manage exception for not finding employee
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
        Employee deleteEmployee = employeeRepository.findById(id).orElseThrow(RuntimeException::new);
        employeeRepository.delete(deleteEmployee);
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
}
