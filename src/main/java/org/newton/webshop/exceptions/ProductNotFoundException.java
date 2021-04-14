package org.newton.webshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends NotFoundException {
    private final static String RESOURCE = "product";

    public ProductNotFoundException(String id) {
        super(RESOURCE, id);
    }
}
