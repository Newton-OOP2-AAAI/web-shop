package org.newton.webshop.models.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

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
