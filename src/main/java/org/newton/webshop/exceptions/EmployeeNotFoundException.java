package org.newton.webshop.exceptions;

public class EmployeeNotFoundException extends NotFoundException {
    private final static String RESOURCE = "employee";

    public EmployeeNotFoundException(String id) {
        super(RESOURCE, id);
    }
}
