package org.newton.webshop.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "categories")
@NoArgsConstructor
@Getter
@Setter
@Entity

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @ManyToMany
    @JoinTable(name = "categories_products", joinColumns = @JoinColumn(name = "category_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "product_id", nullable = false))
    private Set<Product> products;

    @Column(length = 30)
    private String name;


}



