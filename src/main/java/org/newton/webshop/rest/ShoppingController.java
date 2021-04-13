package org.newton.webshop.rest;

import org.newton.webshop.models.dto.creation.CartCreationDto;
import org.newton.webshop.models.dto.creation.ItemCreationDto;
import org.newton.webshop.models.dto.response.CartDto;
import org.newton.webshop.services.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class ShoppingController {
    private final ShoppingService shoppingService;

    @Autowired
    public ShoppingController(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CartDto createCart(@RequestBody CartCreationDto creationDto) {
        return shoppingService.createCart(creationDto);
    }

    @PostMapping(value = "/{cart_id}/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public CartDto addItem(@PathVariable(name = "cart_id") String cartId, @RequestBody ItemCreationDto creationDto) {
        return shoppingService.addItem(cartId, creationDto);
    }

    @PutMapping(value = "/{cart_id}/items/{item_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CartDto modifyItem(@PathVariable(name = "cart_id") String cartId,
                              @PathVariable(name = "item_id") String itemId,
                              @RequestBody ItemCreationDto dto) {
        return shoppingService.updateItem(cartId, itemId, dto);
    }

    @DeleteMapping(value = "/{cart_id}/items/{item_id}")
    public void deleteItem(@PathVariable(name = "cart_id") String cartId,
                           @PathVariable(name = "item_id") String itemId) {
        shoppingService.deleteItem(cartId, itemId);
    }

    @GetMapping(value = "/{cart_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CartDto findCartById(@PathVariable(name = "cart_id") String cartId) {
        return shoppingService.findCart(cartId);
    }
}


