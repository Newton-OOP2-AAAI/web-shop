package org.newton.webshop.models.dto.creation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryCreationDto {

    private String name;
    private String parentCategoryId;
    private Set<String> childCategoryIds;
    private Set<String> productIds;
}
