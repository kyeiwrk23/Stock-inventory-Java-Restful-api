package com.example.inventory.repository;

import com.example.inventory.model.Address;
import com.example.inventory.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {

    @Query("SELECT s FROM Supplier s WHERE s.taxCode = ?1")
    Supplier findSupplierByTaxId(String taxCode);
}
