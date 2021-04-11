package org.newton.webshop.models.dto.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmployeeUpdateDto {

    private String roleId;
    private String firstname;
    private String lastname;
    private String phone;
    private String streetName;
    private String streetNumber;
    private String zipCode;
    private String city;
}
