package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<Product, String> {
    @Override
    List<Product> findAll();

    Page<Product> findByNameContaining(String name, Pageable pageable);


    Page<Product> findAll(Pageable pageable);

    //@Query("SELECT 'product_id' FROM 'categories_products' WHERE 'category_id'==?1")
    @Query("SELECT p FROM Category c JOIN c.products p where c.id = ?1")
    Page<Product> getAllProductsByCategoryId(String categoryId , Pageable pageable);

    @Query("SELECT p FROM Category c JOIN c.products p where c.name = ?1")
    Page<Product> getAllProductsByCategoryName(String name , Pageable pageable);


}
