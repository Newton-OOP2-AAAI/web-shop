package org.newton.webshop.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.newton.webshop.models.Product;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Table(name = "categories")
@Getter
@Setter
@Entity
public class Category {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, nullable = false)
    private String id;

    @Column(length = 30)
    private String name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Category parentCategory;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentCategory")
    private Set<Category> childCategories;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "categories_products",
            joinColumns = @JoinColumn(name = "category_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "product_id", nullable = false)
    )
    private Set<Product> products;

    public Category() {
        this.childCategories = new HashSet<>();
    }

    /**
     * Bidirectional utility method to add a parent category.
     *
     * @param parentCategory the parent category
     */
    public void addParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
        parentCategory.getChildCategories().add(this);
    }

    /**
     * Bidirectional utility method to remove the parent category.
     *
     * @param parentCategory the parent category
     */
    public void removeParentCategory(Category parentCategory) {
        this.parentCategory = null;
        parentCategory.getChildCategories().remove(this);
    }

    /**
     * Bidirectional utility method to add a child category.
     *
     * @param childCategory the child category
     */
    public void addChildCategory(Category childCategory) {
        this.childCategories.add(childCategory);
        childCategory.setParentCategory(this);
    }

    /**
     * Bidirectional utility method to remove a child category.
     *
     * @param childCategory the child category
     */
    public void removeChildCategory(Category childCategory) {
        this.childCategories.remove(childCategory);
        childCategory.setParentCategory(null);
    }

    /**
     * //todo revisit helper methods
     * Bidirectional utility method to add a product to a category.
     *
     * @param product
     */
    public void addProduct(Product product) {
        this.products.add(product);
        product.getCategory().add(this);
    }

    /**
     * Bidirectional utility method to remove a product from a category.
     *
     * @param product
     */
    public void removeProduct(Product product) {
        this.products.remove(product);
        product.getCategory().remove(this);
    }
}



