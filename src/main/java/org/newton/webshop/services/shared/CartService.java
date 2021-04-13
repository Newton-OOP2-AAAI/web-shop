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
     * @throws RuntimeException If resource doesn't exist or id parameter is null
     */
    public Cart findById(String id) {
        if (id == null) {
            throw new RuntimeException(); //todo Exception: Cart not found
        }
        return cartRepository.findById(id).orElseThrow(RuntimeException::new); //todo Exception: Cart not found
    }

    /**
     * Tells if this cart needs a customer (to create a valid order)
     *
     * @param cart
     * @param desiredCustomerId
     * @return
     */
    public boolean cartNeedsCustomer(Cart cart, String desiredCustomerId) {
        //När cart saknar customer och desiredCustomerId = ett existerande desiredCustomerId
        var customer = cart.getCustomer();
        var actualCustomerId = (customer == null) ? null : customer.getId();

        if (desiredCustomerId == null) {
            if (customer == null) {
                return true;
            } else {
                throw new RuntimeException(); //todo Exception mismatching ids
            }
        }

        if (desiredCustomerId.equals(actualCustomerId)) {
            return false;
        }

        throw new RuntimeException(); //todo Exception: Mismatching ids
    }
}

