package org.newton.webshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    private final static String MESSAGE = "Could not find %s with id: %s";

    public NotFoundException(String resource, String id) {
        super(String.format(MESSAGE, resource, id));
    }
}
