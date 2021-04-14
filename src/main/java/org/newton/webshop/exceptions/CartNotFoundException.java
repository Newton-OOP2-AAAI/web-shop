package org.newton.webshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CartNotFoundException extends NotFoundException {
    private static final String RESOURCE = "cart";

    public CartNotFoundException(String id) {

        super(RESOURCE, id);
    }
}
