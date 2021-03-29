package org.newton.webshop.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.newton.webshop.models.entities.Customer;


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

    public CustomerUpdateDto(Customer customer) {
        this.firstname = customer.getFirstname();
        this.lastname = customer.getLastname();
        this.phone = customer.getPhone();
        this.streetName = customer.getAddress().getStreetName();
        this.streetNumber = customer.getAddress().getStreetNumber();
        this.zipCode = customer.getAddress().getZipCode();
        this.city = customer.getAddress().getCity();
    }
}

