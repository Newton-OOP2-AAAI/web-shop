package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Item;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ItemRepository extends CrudRepository<Item, String> {
    @Override
    List<Item> findAll();
}
