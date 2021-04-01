package org.newton.webshop.services;

import org.newton.webshop.models.entities.Inventory;
import org.newton.webshop.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Inventory createInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);

    }

    /**
     * Notes:
     *
     * Lower level services that only handle one repository should return entities.
     * AssortmentService should take care of mapping between Entity/DTO
     *
     */


    //TODO commented code should be removed unless methods are needed in Assortment Service
//    public List<Inventory> findAll() {
//        return inventoryRepository.findAll();
//    }
}