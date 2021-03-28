package org.newton.webshop.rest;

import org.newton.webshop.models.entities.Item;
import org.newton.webshop.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/all")
    List<Item> all() {
        return itemService.findAll();
    }

    @PostMapping
    Item newItem(@RequestBody Item newItem) {
        return itemService.newItem(newItem);
    }

    // Single item

    @GetMapping
    Item one(@RequestParam String id) {
        return itemService.findById(id);
    }

    @PutMapping
    Item replaceItem(@RequestBody Item newItem, @RequestParam String id) {
        return itemService.replaceItem(newItem, id);
    }

    @DeleteMapping
    void deleteItem(@RequestParam String id) {
        itemService.deleteItemById(id);
    }
}
