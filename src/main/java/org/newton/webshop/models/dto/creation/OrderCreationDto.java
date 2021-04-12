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
}
