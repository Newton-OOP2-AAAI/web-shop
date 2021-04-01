package org.newton.webshop.models.dto.creation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.newton.webshop.models.entities.Category;
import org.newton.webshop.models.entities.Product;

import javax.persistence.*;
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
