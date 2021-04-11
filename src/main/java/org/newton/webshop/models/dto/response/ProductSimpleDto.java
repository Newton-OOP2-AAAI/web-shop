package org.newton.webshop.models.dto.response;

import lombok.*;


@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSimpleDto {

    private String id;
    private String name;
    private Integer price;
    private String description;
    private boolean visible;
}
