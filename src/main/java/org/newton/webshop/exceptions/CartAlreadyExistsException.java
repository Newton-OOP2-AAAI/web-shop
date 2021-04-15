package org.newton.webshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CartAlreadyExistsException extends RuntimeException {
    private static final String MESSAGE = "Cart with id: %s already has an order.";

    public CartAlreadyExistsException(String id) {
        super(String.format(MESSAGE, id));
    }
}