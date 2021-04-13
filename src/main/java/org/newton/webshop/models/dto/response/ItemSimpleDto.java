package org.newton.webshop.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ItemSimpleDto {
    private String inventoryId;
    private Integer quantity;
    private String name;
    private Integer price;
    private String size;
    private String color;
}
