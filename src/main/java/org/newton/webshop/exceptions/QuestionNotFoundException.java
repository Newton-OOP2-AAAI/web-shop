package org.newton.webshop.exceptions;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(String id) {
        super("Could not find the question " + id);
    }
}
