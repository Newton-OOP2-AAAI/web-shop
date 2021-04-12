package org.newton.webshop.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
}
