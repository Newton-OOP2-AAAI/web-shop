package org.newton.webshop.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class OrderDto {
    private String id;
    private LocalDateTime orderedOn;
    private Set<ItemSimpleDto> items;
    private String firstname;
    private String lastname;
    private String streetName;
    private String streetNumber;
    private String zipCode;
    private String city;
    private String phone;
}
