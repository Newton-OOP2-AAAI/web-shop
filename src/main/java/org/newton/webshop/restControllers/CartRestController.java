package org.newton.webshop.restControllers;

import org.newton.webshop.exceptions.CartNotFoundException;
import org.newton.webshop.models.Cart;
import org.newton.webshop.repositories.CartRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartRestController {
    private final CartRepository repository;

    CartRestController(CartRepository repository) {
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
    Cart one(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new CartNotFoundException(id));
    }

    @PutMapping("/carts/{id}")
    Cart replaceCart(@RequestBody Cart newCart, @PathVariable Integer id) {

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
    void deleteCart(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
