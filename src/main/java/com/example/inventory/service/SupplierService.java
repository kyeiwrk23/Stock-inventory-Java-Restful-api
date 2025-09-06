package com.example.inventory.service;



import com.example.inventory.dto.request.SupplierRequestDTO;
import com.example.inventory.dto.response.SupplierResponseDTO;
import com.example.inventory.dto.response.SupplierResponseDTOPagination;

import java.util.List;

public interface SupplierService {

    SupplierResponseDTOPagination getAllSuppliers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    SupplierResponseDTO createSupplier(SupplierRequestDTO supplierRequestDTO);

    SupplierResponseDTO getSupplierById(Long supplierId);

    SupplierResponseDTO updateSupply(SupplierRequestDTO supplierRequestDTO, Long supplierId);

    String deleteSupplier(Long supplierId);
}
