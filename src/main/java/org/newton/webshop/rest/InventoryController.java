package org.newton.webshop.rest;

import org.newton.webshop.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Deprecated Should be removed, but keeping it for reference until AssortmentController is fully implemented
 * TODO Remove
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

//    @GetMapping("/all")
//    List<Inventory> findAll() {
//        return inventoryService.findAll();
//    }
}
