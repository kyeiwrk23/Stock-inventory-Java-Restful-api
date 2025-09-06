package com.example.inventory.dto.response;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierResponseDTO {

    @JsonIgnore
    private List<AddItemResponseDTO> items;
    private Long supplierId;
    private String supplierName;
    private String contactPerson;
    private String email;
    private String Phone;
    private String website;
    private String taxCode;
    private String paymentTerms;
    private Boolean isActive;
    private LocalDate createdAt;
    private LocalDate lastUpdated;
    private String notes;
}
