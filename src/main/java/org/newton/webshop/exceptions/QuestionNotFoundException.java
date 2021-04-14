package org.newton.webshop.exceptions;

public class QuestionNotFoundException extends NotFoundException {
    private final static String RESOURCE = "question";

    public QuestionNotFoundException(String id) {
        super(RESOURCE, id);
    }
}
