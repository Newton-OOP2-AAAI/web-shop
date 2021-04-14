package org.newton.webshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class MalformedRequestBodyException extends RuntimeException {
    private final static String MESSAGE = "Dto is malformed: %s can not be empty or null. %s.";


    public MalformedRequestBodyException(String jsonField,String advice) {
        super(String.format(MESSAGE, jsonField, advice));
    }
}
