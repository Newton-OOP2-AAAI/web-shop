package org.newton.webshop.models.dto.creation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * DTO that contains information required to create a new category
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryCreationDto {
    //Acceptable values: Non-null String
    private String name;

    //Acceptable values: Null or id of persisted category
    private String parentCategoryId;

    //Acceptable values: Null, empty set, set of ids (of persisted entities)
    //Note: Set containing null is not accepable
    private Set<String> childCategoryIds;
    private Set<String> productIds;
}
