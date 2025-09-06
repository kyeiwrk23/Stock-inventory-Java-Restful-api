package com.example.inventory.controller;

import com.example.inventory.config.AppConstants;
import com.example.inventory.dto.request.AddressRequestDTO;
import com.example.inventory.dto.request.AddressRequestDTOPagination;
import com.example.inventory.dto.response.AddressResponseDTO;
import com.example.inventory.service.AddressServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class AddressController {

    @Autowired
    private AddressServiceImpl addressService;

    @PostMapping("/addresses")
    public ResponseEntity<?> addAddress(@Valid  @RequestBody AddressRequestDTO addressRequestDTO) {
        AddressResponseDTO addressResponseDTO = addressService.addAddress(addressRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(addressResponseDTO);
    }

    @GetMapping("/addresses")
    public ResponseEntity<?> getAllAddresses(@RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                             @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                             @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_ADDRESS_ID,required = false) String sortBy,
                                             @RequestParam(name = "sortOrder", defaultValue =  AppConstants.SORT_ORDER,required = false) String sortOrder)
    {
        AddressRequestDTOPagination addressDto = addressService.getAllAddress(pageNumber,pageSize,sortBy,sortOrder);
        return ResponseEntity.status(HttpStatus.OK).body(addressDto);
    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<?> getAddress(@PathVariable Long addressId)
    {
        AddressResponseDTO addressResponseDTO = addressService.getAddress(addressId);
        return ResponseEntity.status(HttpStatus.OK).body(addressResponseDTO);
    }

    @GetMapping("/supplier/{supplierId}/addresses")
    public ResponseEntity<List<?>> getSupplierAddresses(@PathVariable Long supplierId){
        List<AddressResponseDTO> addressResponseDTO = addressService.getSupplierAddresses(supplierId);
        return ResponseEntity.status(HttpStatus.OK).body(addressResponseDTO);
    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<?> updateAddress(@Valid @RequestBody AddressRequestDTO addressRequestDTO, @PathVariable Long addressId) {
        AddressResponseDTO addressResponseDTO = addressService.updateAddress(addressRequestDTO,addressId);
        return ResponseEntity.status(HttpStatus.OK).body(addressResponseDTO);
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long addressId) {
        String status = addressService.deleteAddress(addressId);
        return ResponseEntity.status(HttpStatus.OK).body(status);
    }
}
