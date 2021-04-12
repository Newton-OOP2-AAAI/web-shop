package org.newton.webshop.models.dto.creation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RoleCreationDto {

    private String id;
    private Boolean employee;
    private String title;
    private Boolean chatbot;
    private Boolean categories;
    private Boolean products;
}
