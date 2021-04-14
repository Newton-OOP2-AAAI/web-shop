package org.newton.webshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MismatchedIdException extends RuntimeException {
    private final static String MESSAGE = "Could not match %s with %s. %s";


    public MismatchedIdException(String firstId, String secondId, String advice) {
        super(String.format(MESSAGE, firstId, secondId, advice));
    }
}
