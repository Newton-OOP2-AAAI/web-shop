package org.newton.webshop.rest.combined;

import org.newton.webshop.models.dto.creation.ItemCreationDto;
import org.newton.webshop.models.dto.response.CartDto;
import org.newton.webshop.services.combined.ShoppingService;
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
    public CartDto createCart(@RequestBody ItemCreationDto creationDto) {
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

}


