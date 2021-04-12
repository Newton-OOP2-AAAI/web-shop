package org.newton.webshop.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CartDto {
    private String id;
    private Set<ItemDto> items;
    private String customerId;
}
