package org.newton.webshop.exceptions;

public class AnswerNotFoundException extends RuntimeException {
    public AnswerNotFoundException(Integer id) {
        super("Could not found the answer " + id);
    }
}
