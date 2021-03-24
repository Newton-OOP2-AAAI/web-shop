package org.newton.webshop.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.newton.webshop.models.entities.Category;
import org.newton.webshop.models.entities.Inventory;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Table(name = "products")
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, nullable = false)
    private String id;

    @OneToMany(mappedBy = "product")
    private Set<Inventory> inventory;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @ManyToMany
    @JoinTable(name = "categories_products", joinColumns = @JoinColumn(name = "product_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "category_id", nullable = false))
    private Set<Category> category;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean visible;
}