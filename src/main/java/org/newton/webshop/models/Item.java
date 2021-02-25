package org.newton.webshop.models;

import javax.persistence.*;

@Table(name = "items")
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private int id;


    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "quantity")
    private int quantity;

    public Item() {
    }

    public Item(int id, Cart cart, int productId, int quantity) {
        this.id = id;
        this.cart = cart;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
