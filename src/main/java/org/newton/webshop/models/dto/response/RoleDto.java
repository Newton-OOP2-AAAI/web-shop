package org.newton.webshop.models.dto.response;

import lombok.*;

import java.util.Set;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private String id;
    private Set<EmployeeDto> employees;//TODO: Fråga gruppen om det här
    private Boolean employee;
    private String title;
    private Boolean chatbot;
    private Boolean categories;
    private Boolean products;
}
