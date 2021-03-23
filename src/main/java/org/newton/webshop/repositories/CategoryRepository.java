package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, String> {

}
