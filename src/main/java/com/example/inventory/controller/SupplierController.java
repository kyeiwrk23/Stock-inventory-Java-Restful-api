package com.example.inventory.controller;

import com.example.inventory.config.AppConstants;
import com.example.inventory.dto.request.SupplierRequestDTO;
import com.example.inventory.dto.response.SupplierResponseDTO;
import com.example.inventory.dto.response.SupplierResponseDTOPagination;
import com.example.inventory.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/suppliers")
    public ResponseEntity<SupplierResponseDTOPagination> getAllSuppliers(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_SUPPLIER_ID,required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder)
    {
        SupplierResponseDTOPagination supplierResponseDTOS = supplierService.getAllSuppliers(pageNumber,pageSize,sortBy,sortOrder);
        return ResponseEntity.ok(supplierResponseDTOS);

    }

    @PostMapping("/suppliers")
    public ResponseEntity<SupplierResponseDTO> createSupplier(@Valid  @RequestBody SupplierRequestDTO supplierRequestDTO)
    {
        SupplierResponseDTO supplierResponseDTO = supplierService.createSupplier(supplierRequestDTO);
        return new ResponseEntity<>(supplierResponseDTO,HttpStatus.CREATED);
    }

    @GetMapping("/suppliers/{supplierId}")
    public ResponseEntity<SupplierResponseDTO> getSupplierById(@PathVariable Long supplierId) {
        SupplierResponseDTO supplierResponseDTO = supplierService.getSupplierById(supplierId);

        return ResponseEntity.ok(supplierResponseDTO);
    }

    @PutMapping("/suppliers/{supplierId}")
    public ResponseEntity<SupplierResponseDTO> updateSupplier(@Valid @RequestBody SupplierRequestDTO supplierRequestDTO, @PathVariable Long supplierId) {
        SupplierResponseDTO supplierResponse = supplierService.updateSupply(supplierRequestDTO,supplierId);

        return ResponseEntity.ok(supplierResponse);
    }

    @DeleteMapping("/suppliers/{supplierId}")
    public ResponseEntity<String> deleteSupplier(@PathVariable Long supplierId) {
        String deleteSupplier = supplierService.deleteSupplier(supplierId);
        return ResponseEntity.ok(deleteSupplier);
    }
}
