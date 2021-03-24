package org.newton.webshop.services;

import org.newton.webshop.models.entities.Order;

import org.newton.webshop.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Iterable<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(String id) {
        return orderRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Order newOrder(@RequestBody Order newOrder) {
        return orderRepository.save(newOrder);
    }
}

