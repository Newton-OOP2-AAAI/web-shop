package org.newton.webshop.exceptions;

public class RoleNotFoundException extends NotFoundException {
    private final static String RESOURCE = "role";

    public RoleNotFoundException(String id) {
        super(RESOURCE, id);
    }
}
