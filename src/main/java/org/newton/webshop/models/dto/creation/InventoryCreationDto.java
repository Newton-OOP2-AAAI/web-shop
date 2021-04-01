package org.newton.webshop.models.dto.creation;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class InventoryCreationDto {

    private String size;
    private String color;
    private Integer quantity;
}
