package com.example.inventory.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemRequestDTO {
    private Long itemId;

    @NotBlank(message = "Item Name can't be blank")
    @NotNull(message = "Item Name can't be Null")
    @Size(min = 3,max = 50, message = "Item Name must be between 3 and 50 characters ")
    private String itemName;

    @NotBlank(message = "Item description can't be blank")
    @Size(min = 3,max = 200, message = "Description must be between 3 and 200 characters ")
    private String description;

    @Positive(message = "Unit price can't be negative")
    private Double unitPrice;

    @Positive(message = "Unit price can't be negative")
    private Integer stock;

    @Positive(message = "Unit price can't be negative")
    private Long reOrderLevel;

    @Positive(message = "Unit price can't be negative")
    private Long categoryId;

    @NotBlank(message = "Item Status can't be blank")
    @Size(max = 20, message = "Item status must have at most 20 characters ")
    private String status;
}
