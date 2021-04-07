package org.newton.webshop.models.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "items")
@Entity
public class Item {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36)
    private String id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Inventory inventory;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Cart cart;

    @Column(nullable = false)
    private Integer quantity;

    @Column(length = 5)
    private String size;

    @Column(length = 50)
    private String color;

    public void addQuantity(Integer quantity) {
        this.quantity += quantity;
    }
}



