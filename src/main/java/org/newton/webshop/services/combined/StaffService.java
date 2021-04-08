package org.newton.webshop.services.combined;

import org.newton.webshop.models.dto.creation.EmployeeCreationDto;
import org.newton.webshop.models.dto.response.EmployeeDto;
import org.newton.webshop.models.dto.update.EmployeeUpdateDto;
import org.newton.webshop.models.entities.Address;
import org.newton.webshop.models.entities.Employee;
import org.newton.webshop.models.entities.Role;
import org.newton.webshop.repositories.EmployeeRepository;
import org.newton.webshop.services.EmployeeService;
import org.newton.webshop.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public EmployeeDto createEmployee(EmployeeCreationDto creationDto) {
        Role role = roleService.findById(creationDto.getRoleId());
        Employee employee = employeeService.createEmployee(toEntity(creationDto, role));
        return toDto(employee);
    }

    public EmployeeDto editEmployeeById(String employeeId, EmployeeUpdateDto employeeUpdateDto) {
        Employee employee = toEntity(employeeUpdateDto);
        Employee returnEmployee = employeeService.updateEmployee(employee, employeeId);
        returnEmployee.setRole(roleService.findById(employee.getRole().getId()));
        return toDto(returnEmployee);
    }

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


    public EmployeeDto findById(String id) {
        return new EmployeeDto(employeeRepository.findById(id).orElseThrow(RuntimeException::new)); //TODO Manage exception for not finding employee
    }

    public List<EmployeeDto> findAll() {
        return employeeService.findAll()
                .stream()
                .map(StaffService::toDto)
                .collect(Collectors.toList());
    }

    public void deleteEmployeeById(String id) {
        Employee deleteEmployee = employeeRepository.findById(id).orElseThrow(RuntimeException::new);
        employeeRepository.delete(deleteEmployee);
    }

    private static Employee toEntity(EmployeeUpdateDto employeeUpdateDto) {
        return Employee.builder()
                .role(new Role(employeeUpdateDto.getRoleId()))
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
