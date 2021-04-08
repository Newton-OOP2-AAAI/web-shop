package org.newton.webshop.rest.combined;


import org.newton.webshop.models.dto.creation.EmployeeCreationDto;
import org.newton.webshop.models.dto.response.EmployeeDto;
import org.newton.webshop.models.dto.update.EmployeeUpdateDto;
import org.newton.webshop.services.combined.StaffService;
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
    public EmployeeDto editEmployeeById(@RequestParam String id, @RequestBody EmployeeUpdateDto employeeUpdateDto) {
        return staffService.editEmployeeById(id, employeeUpdateDto);
    }

    @GetMapping
    EmployeeDto findEmployeeById(@RequestParam String id) {
        return staffService.findById(id);
    }

    @GetMapping("/all")
    List<EmployeeDto> findAllEmployees() {
        return staffService.findAll();
    }

    @DeleteMapping
    public void deleteEmployeeById(@RequestParam String id) {
        staffService.deleteEmployeeById(id);
    }

}
