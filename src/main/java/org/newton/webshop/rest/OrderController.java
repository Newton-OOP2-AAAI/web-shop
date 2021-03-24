package org.newton.webshop.rest;

import org.newton.webshop.exceptions.OrderNotFoundException;
import org.newton.webshop.models.entities.Order;
import org.newton.webshop.repositories.OrderRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {
    private final OrderRepository repository;

    OrderController(OrderRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/orders")
    Iterable<Order> all() {
        return repository.findAll();
    }

    @PostMapping("/orders")
    Order newOrder(@RequestBody Order newOrder) {
        return repository.save(newOrder);
    }

    // Single item

    @GetMapping("/orders/{id}")
    Order one(@PathVariable String id) {
        return repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @PutMapping("/orders/{id}")
    Order replaceOrder(@RequestBody Order newOrder, @PathVariable String id) {

        return repository.findById(id)
                .map(order -> {
                    order.setOrderOn(newOrder.getOrderOn());
                    order.setCart(newOrder.getCart());
                    return repository.save(order);
                })
                .orElseGet(() -> {
                    newOrder.setId(id);
                    return repository.save(newOrder);
                });
    }

    @DeleteMapping("/orders/{id}")
    void deleteOrder(@PathVariable String id) {
        repository.deleteById(id);
    }
}
