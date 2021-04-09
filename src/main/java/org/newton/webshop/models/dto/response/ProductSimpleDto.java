package org.newton.webshop.models.dto.response;

import lombok.*;


@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSimpleDto {
    private String id; //Kanske ta bort sen men skönt att ha när man testar
    private String name;
    private Integer price;
    private String description;
    private boolean visible;
}
