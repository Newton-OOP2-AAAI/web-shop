package org.newton.webshop.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonManagedReference
    @OneToMany(mappedBy = "cart")
    private Set<Item> items;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

}



