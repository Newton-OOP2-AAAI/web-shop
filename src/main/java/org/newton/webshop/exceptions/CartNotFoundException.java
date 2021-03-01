package org.newton.webshop.exceptions;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(Integer id) {
        super("Could not find cart " + id);
    }
}
