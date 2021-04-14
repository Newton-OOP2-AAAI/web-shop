package org.newton.webshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends NotFoundException {
    private final static String RESOURCE = "category";

    public CategoryNotFoundException(String id) {
        super(RESOURCE, id);
    }
}
