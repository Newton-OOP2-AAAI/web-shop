package org.newton.webshop.services;

import org.newton.webshop.models.dto.creation.OrderCreationDto;
import org.newton.webshop.models.dto.response.ItemSimpleDto;
import org.newton.webshop.models.dto.response.OrderDto;
import org.newton.webshop.models.entities.*;
import org.newton.webshop.services.shared.CartService;
import org.newton.webshop.services.shared.CustomerService;
import org.newton.webshop.services.shared.OrderLowLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles requests by OrderController.
 * Connects shared services needed to handle those requests (one for each repository).
 */
@Service
public class OrderHighLevelService {
    private final OrderLowLevelService orderLowLevelService;
    private final CustomerService customerService;
    private final CartService cartService;

    @Autowired
    public OrderHighLevelService(OrderLowLevelService orderLowLevelService,
                                 CustomerService customerService,
                                 CartService cartService) {
        this.orderLowLevelService = orderLowLevelService;
        this.customerService = customerService;
        this.cartService = cartService;
    }


    /**
     * Create an order. It's optional to refer to an existing customer by customer id or fill out all the other fields to create a new customer.
     *
     * @param dto dto containing details to create the order.
     * @return OrderDto containing details about the order
     */
    public OrderDto createOrder(OrderCreationDto dto) {
        var customerId = dto.getCustomerId();
        var cart = cartService.findById(dto.getCartId());

        //Cart needs customer to create valid order
        if (cart.needsCustomer(customerId)) {
            var customer = customerService.createCustomer(toEntity(dto));
            customer.addCart(cart);
        }

        var order = orderLowLevelService.createOrder(toEntity(cart));

        return toDto(order);
    }

    /**
     * Generate an Order entity
     *
     * @param cart the cart to put in the order
     * @return Order entity
     */
    private static Order toEntity(Cart cart) {
        return Order.builder()
                .cart(cart)
                .orderedOn(LocalDateTime.now())
                .build();
    }

    /**
     * Converts dto to customer.
     *
     * @param dto contains information about the customer creating the order.
     * @return Customer entity (which isn't persisted in the database)
     */
    private static Customer toEntity(OrderCreationDto dto) {
        return Customer.builder()
                .carts(new HashSet<>())
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .address(Address.builder()
                        .streetName(dto.getStreetName())
                        .streetNumber(dto.getStreetNumber())
                        .zipCode(dto.getZipCode())
                        .city(dto.getCity())
                        .build())
                .build();
    }

    /**
     * Converts Order entity to dto.
     *
     * @param order entity to convert
     * @return dto
     */
    private static OrderDto toDto(Order order) {
        //Easier access to composite variables in order
        var cart = order.getCart();
        var customer = cart.getCustomer();
        var address = customer.getAddress();

        return OrderDto.builder()
                .id(order.getId())
                .orderedOn(order.getOrderedOn())
                .items(toDto(cart.getItems()))
                .firstname(customer.getFirstname())
                .lastname(customer.getLastname())
                .streetName(address.getStreetName())
                .streetNumber(address.getStreetNumber())
                .zipCode(address.getZipCode())
                .city(address.getCity())
                .phone(customer.getPhone())
                .build();
    }

    /**
     * Converts item entity to dto.
     *
     * @param item item to convert
     * @return dto
     */
    private static ItemSimpleDto toDto(Item item) {
        var inventory = item.getInventory();
        return ItemSimpleDto.builder()
                .inventoryId(inventory.getId())
                .quantity(item.getQuantity())
                .name(inventory.getProduct().getName())
                .size(item.getSize())
                .color(item.getColor())
                .build();
    }

    /**
     * Converts a set of items to a set of dtos
     *
     * @param items set of entities to convert
     * @return set of dtos
     */
    private static Set<ItemSimpleDto> toDto(Set<Item> items) {
        return items.stream()
                .map(OrderHighLevelService::toDto)
                .collect(Collectors.toSet());
    }
}
