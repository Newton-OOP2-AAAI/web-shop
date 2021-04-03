package org.newton.webshop.models.dto.response;

import lombok.*;
import org.newton.webshop.models.entities.Category;
import org.newton.webshop.models.entities.Inventory;

import java.time.LocalDate;
import java.util.Set;


@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String id;
    private Set<Inventory> inventory; //todo inventory dto
    private String name;
    private Integer price;
    private Set<Category> category; //todo category dto
    private String description;
    private boolean visible;


}
