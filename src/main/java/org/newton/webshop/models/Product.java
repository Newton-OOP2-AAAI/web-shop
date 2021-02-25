package org.newton.webshop.models;

import javax.persistence.*;

@Table(name = "products")
@Entity
public class Product {
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int price;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }
}