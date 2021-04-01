package org.newton.webshop.services.combined;

import org.newton.webshop.models.dto.creation.EmployeeCreationDto;
import org.newton.webshop.models.dto.response.EmployeeDto;
import org.newton.webshop.models.entities.Address;
import org.newton.webshop.models.entities.Employee;
import org.newton.webshop.models.entities.Role;
import org.newton.webshop.services.EmployeeService;
import org.newton.webshop.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService {
    private final EmployeeService employeeService;
    private final RoleService roleService;

    @Autowired
    public StaffService(EmployeeService employeeService, RoleService roleService) {
        this.employeeService = employeeService;
        this.roleService = roleService;
    }

    public EmployeeDto createEmployee(EmployeeCreationDto creationDto) {
        Role role = roleService.findById(creationDto.getRoleId());
        Employee employee = employeeService.createEmployee(toEntity(creationDto, role));
        return toDto(employee);
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

    private static Employee toEntity(EmployeeCreationDto creationDto, Role role, String employeeId) {
        return Employee.builder()
                .id(employeeId)
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
}
