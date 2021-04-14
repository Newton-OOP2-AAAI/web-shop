package org.newton.webshop.exceptions;

public class AnswerNotFoundException extends NotFoundException {
    private final static String RESOURCE = "answer";

    public AnswerNotFoundException(String id) {
        super(RESOURCE, id);
    }
}
