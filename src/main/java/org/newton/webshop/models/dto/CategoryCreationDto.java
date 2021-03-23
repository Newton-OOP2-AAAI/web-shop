package org.newton.webshop.models.dto;

import lombok.Getter;
import lombok.Setter;

//todo Lägg till DTOer

@Getter
@Setter
public class CategoryCreationDto {
    private String name;
    private Integer parentCategoryId;
}
