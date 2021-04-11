package org.newton.webshop.services.shared;

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

    public Cart findById(String id) {
        return cartRepository.findById(id).orElseThrow(RuntimeException::new); //todo Exception: Cart not found
    }
}

