package org.newton.webshop.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Table(name = "carts")
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Integer id;


    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "cart")
    @JsonIgnore
    private Set<Item> items;


    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

}



