package org.newton.webshop.services;

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
//    public Item findById(String id) {
//        return itemRepository.findById(id)
//                .orElseThrow(() -> new ItemNotFoundException(id));
//    }
//
//    public Item replaceItem(Item newItem, String id) {
//        return itemRepository.findById(id)
//                .map(item -> {
//                    item.setCart(newItem.getCart());
//                    item.setProduct(newItem.getProduct());
//                    item.setQuantity(newItem.getQuantity());
//                    item.setSize(newItem.getSize());
//                    return itemRepository.save(item);
//                })
//                .orElseGet(() -> {
//                    newItem.setId(id);
//                    return itemRepository.save(newItem);
//                });
//    }
//
//    public void deleteItemById(String id) {
//        itemRepository.deleteById(id);
//    }
}
