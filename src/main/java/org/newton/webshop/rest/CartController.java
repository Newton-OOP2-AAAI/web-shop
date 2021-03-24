package org.newton.webshop.rest;

import org.newton.webshop.exceptions.CartNotFoundException;
import org.newton.webshop.models.entities.Cart;
import org.newton.webshop.repositories.CartRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {
    private final CartRepository repository;

    CartController(CartRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/carts")
    List<Cart> all() {
        return repository.findAll();
    }

    @PostMapping("/carts")
    Cart newCart(@RequestBody Cart newCart) {
        return repository.save(newCart);
    }

    // Single item


    /**
     * The customer wants to view content in the shopping cart (number of each article, price, name, size) to see what’s there.
     *
     * @param id cart_id (PK in carts)
     * @return Instance of cart
     */
    @GetMapping("/carts/{id}")
    Cart one(@PathVariable String id) {
        return repository.findById(id)
                .orElseThrow(() -> new CartNotFoundException(id));
    }

    @PutMapping("/carts/{id}")
    Cart replaceCart(@RequestBody Cart newCart, @PathVariable String id) {

        return repository.findById(id)
                .map(cart -> {
                    cart.setItems(newCart.getItems());
                    cart.setCustomer(newCart.getCustomer());
                    return repository.save(cart);
                })
                .orElseGet(() -> {
                    newCart.setId(id);
                    return repository.save(newCart);
                });
    }

    @DeleteMapping("/carts/{id}")
    void deleteCart(@PathVariable String id) {
        repository.deleteById(id);
    }
}
