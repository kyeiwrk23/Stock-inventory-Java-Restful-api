package com.example.inventory.dto.response;

import com.example.inventory.model.Supplier;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDTO {
    private Long addressId;
    private String streetName;
    private String city;
    private String state;
    private String zip;
    private String country;
    @JsonIgnore
    private Supplier supplier;
}
