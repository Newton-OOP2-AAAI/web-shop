package org.newton.webshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends NotFoundException {
    private final static String RESOURCE = "order";

    public OrderNotFoundException(String id) {
        super(RESOURCE, id);
    }
}
