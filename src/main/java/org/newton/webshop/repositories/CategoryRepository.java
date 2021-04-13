package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, String> {
    List<Category> findAll();
}
