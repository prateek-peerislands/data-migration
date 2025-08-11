package com.dvdrental.management.repository;

import com.dvdrental.management.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    List<Inventory> findByFilmId(Short filmId);
    List<Inventory> findByStoreId(Short storeId);
    List<Inventory> findByFilmIdAndStoreId(Short filmId, Short storeId);
}
