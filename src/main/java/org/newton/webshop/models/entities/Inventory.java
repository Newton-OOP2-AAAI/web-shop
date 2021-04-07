package org.newton.webshop.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Builder
@Table(name = "inventory")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Inventory {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore //TODO: kolla vilken jsonignore som ska användas
    private Product product;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Item> items;

    @Column(length = 5)
    private String size;

    @Column(length = 50)
    private String color;

    @Column(nullable = false)
    private Integer quantity;
}
