package org.newton.webshop.models.dto.response;

import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AccountDto {
    private String accountId;
    private String customerId;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private String streetName;
    private String streetNumber;
    private String zipCode;
    private String city;
    private String username;
    private LocalDate birthDate;
}
