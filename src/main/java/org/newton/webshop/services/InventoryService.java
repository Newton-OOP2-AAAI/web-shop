package org.newton.webshop.services;

import org.newton.webshop.models.entities.Inventory;
import org.newton.webshop.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Notes:
 * Lower level services that only handle one repository should return entities.
 * AssortmentService should take care of mapping between Entity/DTO
 */
@Service
public class InventoryService {
    private InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Create or update a inventory.
     *
     * @param inventory
     * @return the persisted inventory
     */
    public Inventory save(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }
}