package com.example.inventory.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDTO {

    private Long itemId;
    private String itemName;
    private String ItemName;
    private Long categoryId;
    private String description;
    private Double unitPrice;
    private Integer stock;
    //private Long supplierId;
    private Long reOrderLevel;
    private String status;
}
