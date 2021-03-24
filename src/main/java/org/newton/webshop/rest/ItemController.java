package org.newton.webshop.rest;

import org.newton.webshop.exceptions.ItemNotFoundException;
import org.newton.webshop.models.entities.Item;
import org.newton.webshop.repositories.ItemRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {
    private final ItemRepository repository;

    ItemController(ItemRepository repository) {
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
    Item one(@PathVariable String id) {

        return repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
    }

    @PutMapping("/items/{id}")
    Item replaceItem(@RequestBody Item newItem, @PathVariable String id) {

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
    void deleteItem(@PathVariable String id) {
        repository.deleteById(id);
    }
}
