package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface CategoryRepository extends CrudRepository<Category, String> {
    List<Category> findAll();
}
