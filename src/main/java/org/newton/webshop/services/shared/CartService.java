package org.newton.webshop.services.shared;

import org.newton.webshop.exceptions.CartNotFoundException;
import org.newton.webshop.models.entities.Cart;
import org.newton.webshop.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    /**
     * Find all carts
     *
     * @return List of all carts
     */
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    /**
     * Save cart.
     *
     * @param cart
     * @return saved cart
     */
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    /**
     * Find cart by cart id
     *
     * @param id cart id
     * @return cart entity
     * @throws CartNotFoundException If resource doesn't exist or id parameter is null
     */
    public Cart findById(String id) {
        if (id == null) {
            throw new CartNotFoundException(id);
        }
        return cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(id));
    }


}

