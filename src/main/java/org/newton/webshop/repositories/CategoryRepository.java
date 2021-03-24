package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, String> {

}
