package org.newton.webshop.models.entities;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@Builder
@Setter
@Getter
@NoArgsConstructor
@Table(name = "customers")
@Entity
public class Customer {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, nullable = false)
    private String id;

    @OneToMany(mappedBy = "customer")
    private Set<Cart> carts;

    @OneToOne(mappedBy = "customer")
    private Account account;

    @Column(length = 50, nullable = false)
    private String firstname;

    @Column(length = 50, nullable = false)
    private String lastname;

    @Column(length = 50, nullable = false)
    private String phone;

    @Column(length = 50, nullable = false)
    private String email;

    @Embedded
    private Address address;

    /**
     * Utility method that establishes sets the bidirectional relation between an Account instance
     * Method should only be used to avoid having to use respective setters twice.
     * Method does not check for existing associations on either side and will simply overwrite existing associations.
     * Keep in mind that either the invoking Customer entity or the Account entity should be persisted in the database,
     * otherwise neither can be persisted after this method is called.
     *
     * @param account Account instance.
     */
    public void setAccountAssociation(Account account) {
        this.account = account;
        account.setCustomer(this);
    }

    /**
     * Helper method that manages this customer's association with a cart.
     * Warning: Does not validate if cart already has a customer.
     *
     * @param cart the cart to associate this customer with
     */
    public void addCart(Cart cart) {
        this.getCarts().add(cart);
        cart.setCustomer(this);
    }

    /**
     * Helper method that tells if customer has a cart
     *
     * @return true if customer has cart. False if carts is empty or null.
     */
    public boolean hasCart() {
        if (carts == null) {
            return false;
        }
        return !carts.isEmpty();
    }

    /**
     * Helper method that tells if customer has a order
     *
     * @return true if customer has order. False if carts is empty or null.
     */
    public boolean hasOrder() {
        if (!this.hasCart()) {
            return false;
        }
        return carts.stream().anyMatch(Cart::hasOrder);
    }
}
