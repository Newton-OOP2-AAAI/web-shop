package org.newton.webshop.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CategoryDto {
    private String id;
    private String name;
    private String parentCategoryId;
    private String parentCategoryName;
    private Map<String, String> childCategories;
    private Map<String, String> products;
}
