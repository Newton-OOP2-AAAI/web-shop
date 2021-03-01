package org.newton.webshop.restControllers;

import org.newton.webshop.exceptions.ItemNotFoundException;
import org.newton.webshop.models.Item;
import org.newton.webshop.repositories.ItemRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class ItemRestController {
    private final ItemRepository repository;

    ItemRestController(ItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/items")
    List<Item> all() {
        return repository.findAll();
    }

    @PostMapping("/items")
    Item newItem(@RequestBody Item newItem) {
        return repository.save(newItem);
    }

    // Single item

    @GetMapping("/items/{id}")
    Item one(@PathVariable Integer id) {

        return repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
    }

    @PutMapping("/items/{id}")
    Item replaceItem(@RequestBody Item newItem, @PathVariable Integer id) {

        return repository.findById(id)
                .map(item -> {
                    item.setCart(newItem.getCart());
                    item.setProduct(newItem.getProduct());
                    item.setQuantity(newItem.getQuantity());
                    item.setSize(newItem.getSize());
                    return repository.save(item);
                })
                .orElseGet(() -> {
                    newItem.setId(id);
                    return repository.save(newItem);
                });
    }

    @DeleteMapping("/items/{id}")
    void deleteItem(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
