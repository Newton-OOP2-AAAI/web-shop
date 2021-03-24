package org.newton.webshop.models.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

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

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToOne(mappedBy = "cart")
    private Order order;

}



