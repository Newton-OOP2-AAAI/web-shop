package org.newton.webshop.models.dto.creation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.newton.webshop.models.entities.Category;
import org.newton.webshop.models.entities.Inventory;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreationDto {


    private Set<InventoryCreationDto> inventories;
    private String name;
    private Integer price;
    private Set<String> categoryIds;
    private String description;
    private boolean visible;

}
