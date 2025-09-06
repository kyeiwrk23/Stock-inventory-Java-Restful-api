package com.example.inventory.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierRequestDTO {

    @NotBlank(message = "Supplier Name must not be blank")
    @NotNull(message = "Supplier Name must not be Null")
    private String supplierName;

    @NotBlank(message = "Contact Person's Name  must not be blank")
    @NotNull(message = "Contact Person's Name  must not be Null")
    private String contactPerson;

    @Email(message = "Email must follow the format example@example.com")
    @NotNull(message = "Suppliers Email must not be Null")
    @NotBlank(message = "Suppliers Email must not be Blank")
    private String email;

    @NotNull(message = "Suppliers Phone Contact must not be Null")
    @NotBlank(message = "Suppliers Phone Contact must not be Blank")
    private String phone;

    private String website;

    @NotNull(message = "Suppliers Tax Code  must not be Null")
    @NotBlank(message = "Suppliers Tax Code must not be Blank")
    private String taxCode;

    @NotNull(message = "Payment Terms  must not be Null")
    @NotBlank(message = "Payment Terms  must not be Blank")
    private String paymentTerms;
    private Boolean isActive;
    private LocalDate createdAt;
    private LocalDate lastUpdated;

    @NotNull(message = "Notes  must not be Null")
    @NotBlank(message = "Notes  must not be Blank")
    private String notes;

}
