package org.newton.webshop.repositories;

import org.newton.webshop.models.entities.Inventory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InventoryRepository extends CrudRepository<Inventory, String> {
    @Override
    List<Inventory> findAll();
}
