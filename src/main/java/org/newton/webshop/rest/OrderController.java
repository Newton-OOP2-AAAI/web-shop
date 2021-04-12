package org.newton.webshop.rest;

import org.newton.webshop.models.dto.creation.OrderCreationDto;
import org.newton.webshop.models.dto.response.OrderDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {


    @PostMapping
    public OrderDto createOrder(OrderCreationDto dto) {
        return new OrderDto();
    }
}
