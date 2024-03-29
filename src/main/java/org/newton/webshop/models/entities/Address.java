package org.newton.webshop.models.entities;

import lombok.*;
import org.newton.webshop.models.dto.creation.AccountCreationDto;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter

@Embeddable
public class Address {
    public Address(AccountCreationDto newCustomer) {
        this.streetName = newCustomer.getStreetName();
        this.streetNumber = newCustomer.getStreetNumber();
        this.zipCode = newCustomer.getZipCode();
        this.city = newCustomer.getCity();
    }

    @Column(length = 50, nullable = false)
    private String streetName;

    @Column(length = 50, nullable = false)
    private String streetNumber;

    @Column(length = 50, nullable = false)
    private String zipCode;

    @Column(length = 50, nullable = false)
    private String city;
}
