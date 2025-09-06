package com.example.inventory.service;

import com.example.inventory.dto.request.AddressRequestDTO;
import com.example.inventory.dto.request.AddressRequestDTOPagination;
import com.example.inventory.dto.response.AddressResponseDTO;

import java.util.List;

public interface AddressService {
    AddressResponseDTO addAddress(AddressRequestDTO addressRequestDTO);

    AddressRequestDTOPagination getAllAddress(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    AddressResponseDTO getAddress(Long addressId);


    List<AddressResponseDTO> getSupplierAddresses(Long supplierId);

    AddressResponseDTO updateAddress(AddressRequestDTO addressRequestDTO, Long addressId);

    String deleteAddress(Long addressId);
}
