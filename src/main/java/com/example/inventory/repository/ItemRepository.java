package com.example.inventory.repository;

import com.example.inventory.dto.request.AddItemRequestDTO;
import com.example.inventory.model.Category;
import com.example.inventory.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<InventoryItem,Long> {
    boolean existsByItemName(String itemName);

    boolean existsInventoryItemsByCategory_CategoryIdAndItemNameIgnoreCase(Long categoryCategoryId, String itemName);

    @Query("SELECT u FROM InventoryItem u WHERE lower(u.itemName) = lower(?1) AND  lower(u.category.categoryName) = lower(?2)")
    List<InventoryItem> findByItemNameAndCategoryName(String itemName, String categoryName);

    @Query("SELECT s FROM InventoryItem s WHERE s.stock <= s.reOrderLevel")
    List<InventoryItem> findLowStock();


    List<InventoryItem> findByStockEquals(Integer stock);

    Optional<Object> findBySupplier_SupplierId(Long supplierSupplierId);

    @Query("SELECT si FROM InventoryItem si WHERE si.supplier.supplierId = ?1 AND lower(si.itemName) = lower(?2)")
    Optional<InventoryItem> findBySupplierIdAndItemName(Long supplierId, String itemName);
}
