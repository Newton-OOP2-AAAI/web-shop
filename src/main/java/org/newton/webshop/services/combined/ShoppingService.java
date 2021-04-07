package org.newton.webshop.services.combined;

import org.newton.webshop.models.dto.creation.ItemCreationDto;
import org.newton.webshop.models.dto.response.CartDto;
import org.newton.webshop.models.dto.response.ItemDto;
import org.newton.webshop.models.entities.Cart;
import org.newton.webshop.models.entities.Inventory;
import org.newton.webshop.models.entities.Item;
import org.newton.webshop.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Alexander
 */
@Service
public class ShoppingService {
    private final CartService cartService;
    private final ItemService itemService;
    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final InventoryService inventoryService;

    @Autowired
    public ShoppingService(CartService cartService,
                           ItemService itemService,
                           OrderService orderService,
                           CustomerService customerService,
                           ProductService productService,
                           InventoryService inventoryService) {
        this.cartService = cartService;
        this.itemService = itemService;
        this.orderService = orderService;
        this.customerService = customerService;
        this.productService = productService;
        this.inventoryService = inventoryService;
    }

    /**
     * Create a cart with an item
     *
     * @param dto dto
     * @return CartDto
     */
    public CartDto createCart(ItemCreationDto dto) {
        //Create empty cart and fetch inventory (also contains product) from database
        var cart = cartService.save(new Cart());
        var inventory = inventoryService.findById(dto.getInventoryId());

        //Convert to item entity and save in database
        var item = toEntity(dto, inventory, cart);
        itemService.save(item);

        //Update the local cart entity manually so it doesn't have to be fetched again
        cart.getItems().add(item);

        //Return dto
        return toDto(cart);
    }


    /**
     * Add an item to an existing cart. If there is already an identical item in the cart, it will increase the quantity of that item instead.
     *
     * @param cartId which cart item is added to
     * @param dto    dto
     * @return dto
     */
    public CartDto addItem(String cartId, ItemCreationDto dto) {
        //Fetch cart and inventory (also contains a product) from database
        var cart = cartService.findById(cartId);
        var inventoryId = dto.getInventoryId();

        //Optional is present if the item were trying to add already existed in the cart
        var optionalExistingItem = findExistingItem(cart, inventoryId);
        Item newItem;

        //Adding an item is handled differently depending on whether the item already existed in the cart
        if (optionalExistingItem.isPresent()) {

            //If item already existed in cart, modify the amount of that item
            newItem = optionalExistingItem.get();
            newItem.addQuantity(dto.getQuantity());
        } else {

            //If item didn't exist in cart, fetch the inventory and generate an item
            var inventory = inventoryService.findById(inventoryId);

            //Convert to item entity
            newItem = toEntity(dto, inventory, cart);

            //Update the local cart entity manually so it doesn't have to be fetched again
            cart.getItems().add(newItem);
        }
        //Save the item in database and return cart dto
        itemService.save(newItem);
        return toDto(cart);
    }

    /**
     * Updates the desired item in a cart.
     * If we update 'Item A' and the cart already has 'Item B' with the desired new properties of 'Item A', then 'Item A' is deleted and the quantity of 'Item B' is updated instead.
     * In that case, the desired quantity of 'Item A' will be added to the original quantity of 'Item B'.
     *
     * @param cartId id of cart
     * @param itemId id of item
     * @param dto    dto that holds information to update item with
     * @return CartDto
     */
    public CartDto updateItem(String cartId, String itemId, ItemCreationDto dto) {
        //todo Bug: if desired quantity is 0, item should be removed instead
        //todo Bug: if desired quantity is less than 0, throw exception
        //todo Bug: make sure desired quantity doesn't cause int overflow, maybe by comparing with a smaller number like quantity in stock

        //Fetch given cart from database
        var cart = cartService.findById(cartId);

        //The desired quantity
        var desiredQuantity = dto.getQuantity();

        //Create a reference to the item that should be updated
        var itemToUpdate = cart.getItems().stream()
                .filter(oneItem -> oneItem.getId().equals(itemId))
                .findFirst()
                .orElseThrow(RuntimeException::new); //todo Exception: CartId and ItemId doesn't match

        //Get the new inventory
        var newInventoryId = dto.getInventoryId();
        var oldInventory = itemToUpdate.getInventory();


        //Variable to hold the item we actually want to update
        Item actualItemToUpdate;

        //Check if the desired properties for our item already exists in another item
        var optionalExistingItem = findExistingItem(cart, newInventoryId);
        if (optionalExistingItem.isPresent()) {
            //If we found a matching item, that is the item we actually want to update
            actualItemToUpdate = optionalExistingItem.get();
            //Add the desired quantity
            actualItemToUpdate.addQuantity(desiredQuantity);
            //Remove the item we originally wanted to update
            cart.getItems().remove(itemToUpdate);
            itemService.delete(itemToUpdate);
        } else {
            //The actual item we want to update is references the item we originally wanted to update
            actualItemToUpdate = itemToUpdate;
            //Fetch the new inventory if necessary
            var newInventory = inventoryService.getNewInventory(newInventoryId, oldInventory);

            //Set the desired properties
            actualItemToUpdate.setInventory(newInventory);
            actualItemToUpdate.setQuantity(desiredQuantity);
            actualItemToUpdate.setColor(newInventory.getColor());
            actualItemToUpdate.setSize(newInventory.getSize());
        }

        itemService.save(actualItemToUpdate);
        return toDto(cart);
    }

    /**
     * Find item in cart with matching inventory id.
     *
     * @param cart        cart to look in
     * @param inventoryId inventory id to look for
     * @return Optional that is either empty or contains the matching item
     */
    private Optional<Item> findExistingItem(Cart cart, String inventoryId) {
        return cart.getItems()
                .stream()
                .filter(item -> item.getInventory().getId().equals(inventoryId))
                .findFirst();
    }

    /**
     * Converts ItemCreationDto to entity. Associated entites are supplied as parameters
     *
     * @param dto       ItemCreationDto
     * @param inventory an inventory which is already persisted, and therefore also contains a product
     * @param cart      a cart which is already persisted
     * @return item (which is not persisted in database)
     */
    private static Item toEntity(String itemId, ItemCreationDto dto, Inventory inventory, Cart cart) {
        return Item.builder()
                .inventory(inventory)
                .cart(cart)
                .quantity(dto.getQuantity())
                .size(inventory.getSize())
                .color(inventory.getColor())
                .build();
    }

    /**
     * Converts ItemCreationDto to entity. Associated entites are supplied as parameters
     *
     * @param dto       ItemCreationDto
     * @param inventory an inventory which is already persisted, and therefore also contains a product
     * @param cart      a cart which is already persisted
     * @return item (which is not persisted in database)
     */
    private static Item toEntity(ItemCreationDto dto, Inventory inventory, Cart cart) {
        return Item.builder()
                .inventory(inventory)
                .cart(cart)
                .quantity(dto.getQuantity())
                .size(inventory.getSize())
                .color(inventory.getColor())
                .build();
    }

    /**
     * Convert cart entity to dto
     *
     * @param item the item that should be converted
     * @return dto
     */
    private static ItemDto toDto(Item item) {
        return ItemDto.builder()
                .itemId(item.getId())
                .inventoryId(item.getInventory().getId())
                .quantity(item.getQuantity())
                .name(item.getInventory().getProduct().getName())
                .price(item.getInventory().getProduct().getPrice())
                .size(item.getSize())
                .color(item.getColor())
                .description(item.getInventory().getProduct().getDescription())
                .build();
    }

    /**
     * Convert cart entity to dto
     *
     * @param cart the cart that should be converted
     * @return dto
     */
    private static CartDto toDto(Cart cart) {
        return CartDto.builder()
                .id(cart.getId())
                .items(cart.getItems()
                        .stream()
                        .map(ShoppingService::toDto)
                        .collect(Collectors.toSet()))
                .build();
    }
}
