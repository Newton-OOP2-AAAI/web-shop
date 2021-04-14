package org.newton.webshop.services;

import org.newton.webshop.models.dto.creation.CartCreationDto;
import org.newton.webshop.models.dto.creation.ItemCreationDto;
import org.newton.webshop.models.dto.response.CartDto;
import org.newton.webshop.models.dto.response.ItemDto;
import org.newton.webshop.models.entities.Cart;
import org.newton.webshop.models.entities.Customer;
import org.newton.webshop.models.entities.Inventory;
import org.newton.webshop.models.entities.Item;
import org.newton.webshop.services.shared.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Service that handles requests from ShoppingController, which takes care of the shopping carts, from adding items to creating an order
 * Each entity has it's own lower-level service because some of them are used by other controllers.
 */
@Service
public class ShoppingService {
    private final CartService cartService;
    private final ItemService itemService;
    private final OrderLowLevelService orderService;
    private final CustomerService customerService;
    private final InventoryService inventoryService;

    @Autowired
    public ShoppingService(CartService cartService,
                           ItemService itemService,
                           OrderLowLevelService orderService,
                           CustomerService customerService,
                           ProductService productService,
                           InventoryService inventoryService) {
        this.cartService = cartService;
        this.itemService = itemService;
        this.orderService = orderService;
        this.customerService = customerService;
        this.inventoryService = inventoryService;
    }

    /**
     * Create a cart with an item.
     * This method currently treats all customers (logged in or not) as anonymous.
     *
     * @param dto dto
     * @return CartDto
     */
    public CartDto createCart(CartCreationDto dto) {
        var customerId = dto.getCustomerId();
        var customer = (customerId == null) ? null : customerService.findById(customerId);

        //Create empty cart and fetch inventory (also contains product) from database
        var cart = cartService.save(toEntity(customer));
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
        var optionalExistingItem = cart.findItemByInventoryId(inventoryId);
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
     * @param itemId id of item
     * @param dto    dto that holds information to update item with
     * @return CartDto
     */
    public CartDto updateItem(String itemId, ItemCreationDto dto) {
        var item = itemService.findById(itemId);
        var cart = item.getCart();
        var desiredQuantity = dto.getQuantity();

        //Get the new inventory
        var newInventoryId = dto.getInventoryId();
        var oldInventory = item.getInventory();

        //Variable to hold the item we actually want to update
        Item actualItemToUpdate;

        //Check if the cart contains another item with the desired properties
        var optionalExistingItem = cart.findItemByInventoryId(newInventoryId);
        if (optionalExistingItem.isPresent()) {
            //If we found a matching item, that is the item we actually want to update
            actualItemToUpdate = optionalExistingItem.get();
            //Add the desired quantity
            actualItemToUpdate.addQuantity(desiredQuantity);
            //Remove the item we originally wanted to update
            cart.getItems().remove(item);
            itemService.delete(item);
        } else {
            //The actual item we want to update is references the item we originally wanted to update
            actualItemToUpdate = item;
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
     * Delete item from cart
     *
     * @param itemId id of item to delete
     */
    public void deleteItem(String itemId) {
        var item = itemService.findById(itemId);
        var cart = item.getCart();

        //Need to remove the item from the cart variable because the item we want to delete is persisted here
        cart.getItems().remove(item);

        //Delete item
        itemService.delete(item);
    }

    /**
     * Find a cart by cart id
     *
     * @param cartId cart id
     * @return CartDto
     */
    public CartDto findCart(String cartId) {
        var cart = cartService.findById(cartId);
        return toDto(cart);
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
    private static Item toEntity(CartCreationDto dto, Inventory inventory, Cart cart) {
        return Item.builder()
                .inventory(inventory)
                .cart(cart)
                .quantity(dto.getQuantity())
                .size(inventory.getSize())
                .color(inventory.getColor())
                .build();
    }

    /**
     * Converts CartCreation to Item entity. Associated entites are supplied as parameters
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
     * Creates Cart entity from a customer entity. No dto is required.
     *
     * @param customer the customer creating the cart. Leave as null if the cart is created anonymously (no logged-in user).
     * @return Cart entity
     */
    private static Cart toEntity(Customer customer) {
        return Cart.builder()
                .items(new HashSet<>())
                .customer(customer)
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
        //Handle NullPointerException if cart doesn't belong to a customer
        var customer = cart.getCustomer();
        var customerId = (customer == null) ? null : customer.getId();

        //Build dto
        return CartDto.builder()
                .id(cart.getId())
                .items(cart.getItems()
                        .stream()
                        .map(ShoppingService::toDto)
                        .collect(Collectors.toSet()))
                .customerId(customerId)
                .build();
    }
}
