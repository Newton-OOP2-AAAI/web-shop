package org.newton.webshop.exceptions;

public class AnswerNotFoundException extends RuntimeException {
    public AnswerNotFoundException(String id) {
        super("Could not find the answer " + id);
    }
}
