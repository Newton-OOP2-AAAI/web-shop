package org.newton.webshop.models.dto.creation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartCreationDto {
    private String customerId;
    private String inventoryId;
    private Integer quantity;
}
