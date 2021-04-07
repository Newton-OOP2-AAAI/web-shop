package org.newton.webshop.services;

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

    /**
     * Notes:
     *
     * Lower level services that only handle one repository should return entities.
     * AssortmentService should take care of mapping between Entity/DTO
     *
     */


    //TODO commented code should be removed unless methods are needed in Assortment Service
//    public List<Item> findAll() {
//        return itemRepository.findAll();
//    }
//
//    public Item newItem(Item newItem) {
//        return itemRepository.save(newItem);
//    }
//

//

//
//    public void deleteItemById(String id) {
//        itemRepository.deleteById(id);
//    }
}
