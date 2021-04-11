package org.newton.webshop.services.shared;

import org.newton.webshop.models.entities.Item;
import org.newton.webshop.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public Item findById(String id) {
        return itemRepository.findById(id).orElseThrow(RuntimeException::new); //todo Exception: Item not found
    }

    public void delete(Item item) {
        itemRepository.delete(item);
    }

    public void delete(String itemId) {
        itemRepository.deleteById(itemId);
    }

    /**
     * Notes:
     *
     * Lower level services that only handle one repository should return entities.
     * AssortmentService should take care of mapping between Entity/DTO
     *
     */
}
