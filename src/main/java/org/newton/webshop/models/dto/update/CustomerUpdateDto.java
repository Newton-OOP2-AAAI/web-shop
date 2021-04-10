package org.newton.webshop.models.dto.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateDto {
    private String firstname;
    private String lastname;
    private String phone;
    private String streetName;
    private String streetNumber;
    private String zipCode;
    private String city;
}

