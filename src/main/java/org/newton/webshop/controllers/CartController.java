package org.newton.webshop.controllers;

import org.newton.webshop.exceptions.CartNotFoundException;
import org.newton.webshop.models.Cart;
import org.newton.webshop.repositories.CartRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CartController {
    private final CartRepository repository;

    CartController(CartRepository repository) {
        this.repository = repository;
    }

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
}
