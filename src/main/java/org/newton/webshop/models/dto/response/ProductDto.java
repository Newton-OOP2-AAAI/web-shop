package org.newton.webshop.models.dto.response;

import lombok.*;
import org.newton.webshop.models.entities.*;

import java.util.Set;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String id;
    private Set<Inventory> inventory;
    private String name;
    private Integer price;
    private Set<Category> category;
    private String description;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.description = product.getDescription();
        this.inventory = product.getInventory();
        this.name= product.getName();
        this.price=product.getPrice();
        this.category=product.getCategory();
        this.description= product.getDescription();

    }


    public ProductDto(ProductDto productDto) {
        this.id = productDto.getId();
        this.inventory = productDto.getInventory();
        this.name= productDto.getName();
        this.price= productDto.getPrice();
        this.category= productDto.getCategory();
        this.description= productDto.getDescription();
    }
}
