package org.newton.webshop.exceptions;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String id){
        super("Could not find role " + id);
    }
}
