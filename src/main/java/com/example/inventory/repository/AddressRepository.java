package com.example.inventory.repository;

import com.example.inventory.model.Address;
import com.example.inventory.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findAddressBySupplier_SupplierId(Long supplierSupplierId);

    @Query("SELECT ad FROM Address  ad WHERE ad.supplier.supplierId = ?1 AND ad.zip = ?2")
    Address findAddressByZipCodeAndSupplierId(Long supplierId, String zip);

    @Query("SELECT ad FROM Address ad WHERE ad.supplier.supplierId = ?1")
    List<Address> findAddressBySupplier(Long supplierId);
}
