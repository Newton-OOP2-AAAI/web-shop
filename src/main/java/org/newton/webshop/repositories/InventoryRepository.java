package org.newton.webshop.repositories;

import org.newton.webshop.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Integer>{
}
