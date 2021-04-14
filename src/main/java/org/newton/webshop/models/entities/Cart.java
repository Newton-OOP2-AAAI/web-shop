package org.newton.webshop.models.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "carts")
@Entity
public class Cart {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, nullable = false)
    private String id;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "cart")
    @JsonIgnore
    private Set<Item> items;

    @OneToOne(mappedBy = "cart")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /**
     * Constructor to create an empty cart. Other entities have conversion. When
     */
    public Cart() {
        this.items = new HashSet<>();
    }

    /**
     * Helper method that finds item in cart with matching inventory id.
     *
     * @param inventoryId inventory id to look for
     * @return Optional that is either empty or contains the matching item
     */
    public Optional<Item> findItemByInventoryId(String inventoryId) {
        return this.getItems()
                .stream()
                .filter(item -> item.getInventory().getId().equals(inventoryId))
                .findFirst();
    }

    /**
     * Helper method that finds item in cart with matching inventory id.
     *
     * @param itemId inventory id to look for
     * @return Optional that is either empty or contains the matching item
     */
    public Optional<Item> findItembyItemId(String itemId) {
        return this.getItems()
                .stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst();
    }

    /**
     * Tells if this cart needs a customer (to create a valid order)
     *
     * @param desiredCustomerId
     * @return
     */
    public boolean needsCustomer(String desiredCustomerId) {
        //När cart saknar customer och desiredCustomerId = ett existerande desiredCustomerId
        var customer = this.getCustomer();
        var actualCustomerId = (customer == null) ? null : customer.getId();

        if (desiredCustomerId == null) {
            if (customer == null) {
                return true;
            } else {
                throw new RuntimeException(); //todo Exception mismatching ids
            }
        }

        if (desiredCustomerId.equals(actualCustomerId)) {
            return false;
        }

        throw new RuntimeException(); //todo Exception: Mismatching ids
    }

}



