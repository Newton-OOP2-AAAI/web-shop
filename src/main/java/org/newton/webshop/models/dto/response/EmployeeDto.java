package org.newton.webshop.models.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.newton.webshop.models.entities.Employee;

@Builder
@AllArgsConstructor
@Setter
@Getter
public class EmployeeDto {
    private String Id;
    private String role;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private String streetName;
    private String streetNumber;
    private String zipCode;
    private String city;
    private String username;

    public EmployeeDto(Employee employee) {
        this.Id = employee.getId();
        this.role = employee.getRole().getTitle();
        this.firstname = employee.getFirstname();
        this.lastname = employee.getLastname();
        this.phone = employee.getPhone();
        this.email = employee.getEmail();
        this.streetName = employee.getAddress().getStreetName();
        this.streetNumber = employee.getAddress().getStreetNumber();
        this.zipCode = employee.getAddress().getZipCode();
        this.city = employee.getAddress().getCity();
        this.username = employee.getUsername();
    }
}
