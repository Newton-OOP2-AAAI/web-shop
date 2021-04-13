package org.newton.webshop.rest;

import org.newton.webshop.models.dto.creation.OrderCreationDto;
import org.newton.webshop.models.dto.response.OrderDto;
import org.newton.webshop.services.OrderHighLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderHighLevelService orderHighLevelService;

    @Autowired
    public OrderController(OrderHighLevelService orderHighLevelService) {
        this.orderHighLevelService = orderHighLevelService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDto createOrderAndCustomer(@RequestBody OrderCreationDto orderCreationDto) {
        return orderHighLevelService.createOrder(orderCreationDto);
    }
}
