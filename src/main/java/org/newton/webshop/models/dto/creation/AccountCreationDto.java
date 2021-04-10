package org.newton.webshop.models.dto.creation;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreationDto {
    private String firstname;
    private String lastname;
    private String phone;
    private String email;

    private String streetName;
    private String streetNumber;
    private String zipCode;
    private String city;

    private String username;
    private String password;
    private LocalDate birthDate;
}
