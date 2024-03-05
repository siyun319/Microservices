package com.siyun.inventoryservice.repository;

import com.siyun.inventoryservice.model.Inventory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{

   Optional<Inventory> findBySkuCode(String skuCode);
    
}
