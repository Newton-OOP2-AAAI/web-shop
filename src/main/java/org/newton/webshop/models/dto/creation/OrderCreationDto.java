package org.newton.webshop.models.dto.creation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderCreationDto {
    private String cartId;
    private String customerId;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private String streetName;
    private String streetNumber;
    private String zipCode;
    private String city;
}
