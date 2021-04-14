package org.newton.webshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends NotFoundException {
    private final static String RESOURCE = "customer";

    public CustomerNotFoundException(String id) {
        super(RESOURCE, id);
    }

}
