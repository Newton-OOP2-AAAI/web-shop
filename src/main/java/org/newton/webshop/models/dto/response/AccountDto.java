package org.newton.webshop.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.newton.webshop.models.entities.Account;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AccountDto {

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

    public AccountDto(Account test) {
        this.firstname = test.getCustomer().getFirstname();
        this.lastname = test.getCustomer().getLastname();
        this.phone = test.getCustomer().getPhone();
        this.email = test.getCustomer().getEmail();
        this.streetName = test.getCustomer().getAddress().getStreetName();
        this.streetNumber = test.getCustomer().getAddress().getStreetNumber();
        this.zipCode = test.getCustomer().getAddress().getZipCode();
        this.city = test.getCustomer().getAddress().getCity();
        this.username = test.getUsername();
        this.password = test.getPassword();
        this.birthDate = test.getBirthDate();
    }
}
