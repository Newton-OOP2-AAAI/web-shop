package org.newton.webshop.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@NoArgsConstructor
@Setter
@Getter

@Embeddable
public class Address {

    @Column(length = 50, nullable = false)
    private String streetName;

    @Column(length = 50, nullable = false)
    private String streetNumber;

    @Column(name = "zip_code", length = 50, nullable = false)
    private String zipCode;

    @Column(length = 50, nullable = false)
    private String city;
}
