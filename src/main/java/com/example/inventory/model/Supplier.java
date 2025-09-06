package com.example.inventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;
    private String supplierName;
    private String contactPerson;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "supplier", cascade= CascadeType.REMOVE,orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Address> address = new ArrayList<>();

    private String website;
    private String taxCode;
    private String paymentTerms;

    private Boolean isActive;
    private LocalDate createdAt;
    private LocalDate lastUpdated;
    private String notes;

    @OneToMany(mappedBy = "supplier",cascade = {CascadeType.PERSIST,CascadeType.MERGE},orphanRemoval = true,fetch = FetchType.EAGER)
    private List<InventoryItem> items = new ArrayList<>();

}
