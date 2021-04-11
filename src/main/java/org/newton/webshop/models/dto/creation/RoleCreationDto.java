package org.newton.webshop.models.dto.creation;

import lombok.*;

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
