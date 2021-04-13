package org.newton.webshop.services.shared;

import org.newton.webshop.models.entities.Inventory;
import org.newton.webshop.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
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
     * Find inventory entity by inventory id.
     *
     * @param id id of inventory
     * @return Inventory entity
     * @throws RuntimeException if id is null or no resource was found
     */
    public Inventory findById(String id) {
        if (id == null) {
            throw new RuntimeException(); //todo Exception: Inventory not found
        }
        return inventoryRepository.findById(id).orElseThrow(RuntimeException::new);//todo Exception: Inventory not found
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

    public Inventory updateInventories(String inventoryId, Inventory inventory) {
        return inventoryRepository.findById(inventoryId).map(inventoryUpdate -> {
            inventoryUpdate.setSize(inventory.getSize());
            inventoryUpdate.setColor(inventory.getColor());
            inventoryUpdate.setQuantity(inventory.getQuantity());
            return inventoryRepository.save(inventoryUpdate);
        }).orElseThrow(RuntimeException::new);//todo Exception: Inventory not found
    }

    public void deleteInventory(String id) {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(RuntimeException::new);
        inventoryRepository.delete(inventory);

    }

    /**
     * Compares new and old inventory ids and fetches the new inventory from database if necessary
     *
     * @param newInventoryId new inventory id
     * @param oldInventory   old inventory entity
     * @return the new inventory entity
     */
    public Inventory getNewInventory(@NonNull String newInventoryId, @NonNull Inventory oldInventory) {
        var oldInventoryId = oldInventory.getId();
        return (newInventoryId.equals(oldInventoryId))
                ? oldInventory
                : inventoryRepository.findById(newInventoryId).orElseThrow(RuntimeException::new);
    }
}