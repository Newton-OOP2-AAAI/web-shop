package org.newton.webshop.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "inventory")
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(length = 5)
    private String size;

    @Column(length = 50)
    private String color;

    @Column(nullable = false)
    private Integer quantity;

}
