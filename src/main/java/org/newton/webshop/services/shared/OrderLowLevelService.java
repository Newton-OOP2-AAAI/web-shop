package org.newton.webshop.services.shared;

import org.newton.webshop.exceptions.OrderNotFoundException;
import org.newton.webshop.models.entities.Order;
import org.newton.webshop.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderLowLevelService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderLowLevelService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Iterable<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(String id) {
        return orderRepository.findById(id).orElseThrow(()-> new OrderNotFoundException(id));
    }

    public Order createOrder(Order newOrder) {
        return orderRepository.save(newOrder);
    }
}

