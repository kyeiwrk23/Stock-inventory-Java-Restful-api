package com.example.inventory.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDTO {
    private Long addressId;
    private String streetName;
    private String city;
    private String state;
    private String zip;
    private String country;
    private Long supplierId;
}
