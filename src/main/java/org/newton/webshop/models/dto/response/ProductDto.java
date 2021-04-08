package org.newton.webshop.models.dto.response;

import lombok.*;
import org.newton.webshop.models.entities.Category;
import org.newton.webshop.models.entities.Inventory;

import java.util.Map;
import java.util.Set;


@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String id;
    private Set<InventoryDto> inventory;
    private String name;
    private Integer price;
    private Map<String, String> categories;
    private String description;
    private boolean visible;


}
