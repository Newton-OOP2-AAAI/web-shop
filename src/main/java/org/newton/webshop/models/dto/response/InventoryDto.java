package org.newton.webshop.models.dto.response;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDto {

    private String id;
    private String size;
    private String color;
    private Integer quantity;
}
