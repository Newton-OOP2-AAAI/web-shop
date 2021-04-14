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

    /**
     * Find cart by cart id
     *
     * @param cartId cart id
     * @return cart dto
     */
    @GetMapping(params = {"id"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public CartDto findCartById(@RequestParam(name = "id") String cartId) {
        return shoppingService.findCart(cartId);
    }

    /**
     * Create cart
     *
     * @param creationDto dto containing fields to create a cart. Provide customerId if cart is created by logged-in user. Otherwise leave field as null.
     * @return cart dto
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CartDto createCart(@RequestBody CartCreationDto creationDto) {
        return shoppingService.createCart(creationDto);
    }

    /**
     * Add an item to an existing cart.
     * If there is already an identical item in the cart, it will increase the quantity of that item instead.
     *
     * @param cartId      id of cart to add item to
     * @param creationDto dto containing required fields
     * @return cart dto
     */
    @PostMapping(path = "/{cart_id}/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public CartDto addItem(@PathVariable(name = "cart_id") String cartId, @RequestBody ItemCreationDto creationDto) {
        return shoppingService.addItem(cartId, creationDto);
    }

    /**
     * Updates the desired item in a cart.
     * If we update 'Item A' and the cart already has 'Item B' with the desired new properties of 'Item A', then 'Item A' is deleted and the quantity of 'Item B' is updated instead.
     * In that case, the desired quantity of 'Item A' will be added to the original quantity of 'Item B'.
     *
     * @param itemId id of item to update
     * @param dto    dto containing fields to update
     * @return cart dto
     */
    @PutMapping(path = "/items/{item_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CartDto modifyItem(@PathVariable(name = "item_id") String itemId,
                              @RequestBody ItemCreationDto dto) {
        return shoppingService.updateItem(itemId, dto);
    }

    /**
     * Remove item in cart
     *
     * @param itemId id of item to remove
     */
    @DeleteMapping(path = "/items/{item_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteItem(@PathVariable(name = "item_id") String itemId) {
        shoppingService.deleteItem(itemId);
    }
}


