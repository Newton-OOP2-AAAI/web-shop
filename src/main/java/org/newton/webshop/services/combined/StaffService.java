package org.newton.webshop.services.combined;

import org.newton.webshop.models.dto.creation.EmployeeCreationDto;
import org.newton.webshop.models.dto.response.EmployeeDto;
import org.newton.webshop.models.entities.Employee;
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
        return null;
    }

    private static Employee toEntity(EmployeeCreationDto creationDto) {
        Employee.builder()
                .firstname(creationDto.getFirstname())
                .lastname(creationDto.getLastname())
                .phone(creationDto.getPhone())
                .email(creationDto.getEmail())
                .streetName(creationDto.getStreetName())
                .streetNumber(creationDto.getStreetNumber())
                .zipCode(creationDto.getZipCode())
                .city(creationDto.getCity())
                .username(creationDto.getUsername())
                .password(creationDto.getPassword());

//        Employee employee = new Employee();
//        employee.setFirstname(creationDto.getFirstname());
//        employee.setLastname(creationDto.getLastname());
//
    }
}
