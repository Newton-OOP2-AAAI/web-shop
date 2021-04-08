package org.newton.webshop.models.dto.update;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class ProductUpdateDto {
    private String name;
    private Integer price;
    private Set<String> categoryIds;
    private String description;
    private boolean visible;
}
