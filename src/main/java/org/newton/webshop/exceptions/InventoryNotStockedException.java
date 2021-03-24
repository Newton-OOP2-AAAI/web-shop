package org.newton.webshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InventoryNotStockedException extends RuntimeException {
    public InventoryNotStockedException(Integer id) {
        super("Product sold out or no longer available in inventory." + id);
    }
}
