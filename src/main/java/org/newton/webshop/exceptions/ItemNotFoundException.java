package org.newton.webshop.exceptions;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(Integer id) {
        super("Could not find item " + id);
    }
}
