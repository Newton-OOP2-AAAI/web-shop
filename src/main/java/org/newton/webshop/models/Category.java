package org.newton.webshop.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "categories")
//@NoArgsConstructor
@Getter
@Setter
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @ManyToMany
    @JoinTable(
            name = "category_parent_categories",
            joinColumns = @JoinColumn(name = "parent_category_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "child_category_id", nullable = false)
    )
    private Set<Category> parentCategories;

    @ManyToMany
    @JoinTable(
            name = "category_parent_categories",
            joinColumns = @JoinColumn(name = "child_category_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "parent_category_id", nullable = false)
    )
    private Set<Category> childCategories;

    @ManyToMany
    @JoinTable(
            name = "categories_products",
            joinColumns = @JoinColumn(name = "category_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "product_id", nullable = false)
    )
    private Set<Product> products;

    @Column(length = 30)
    private String name;

    public Category() {
        childCategories = new HashSet<>();
        parentCategories = new HashSet<>();
    }

    /**
     * Adds a parent category and corresponding child category, from the perspective of the instance invoking the method.
     * @param parentCategory the parent category
     */
    public void addParentCategory(Category parentCategory) {
        this.parentCategories.add(parentCategory);
        parentCategory.getChildCategories().add(this);
    }

    /**
     * Removes a parent category and corresponding child category, from the perspective of the instance invoking the method.
     * @param parentCategory the parent category
     */
    public void removeParentCategory(Category parentCategory) {
        this.parentCategories.remove(parentCategory);
        parentCategory.getChildCategories().remove(this);
    }

    /**
     * Adds a child category and corresponding parent category, from the perspective of the instance invoking the method.
     * @param childCategory the child category
     */
    public void addChildCategory(Category childCategory) {
        this.childCategories.add(childCategory);
        childCategory.getParentCategories().add(this);
    }

    /**
     * Removes a child category and corresponding parent category, from the perspective of the instance invoking the method.
     * @param childCategory the child category
     */
    public void removeChildCategory(Category childCategory) {
        this.childCategories.remove(childCategory);
        childCategory.getParentCategories().remove(this);
    }
}



