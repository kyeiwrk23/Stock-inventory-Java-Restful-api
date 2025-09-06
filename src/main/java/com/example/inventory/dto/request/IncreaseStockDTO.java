package com.example.inventory.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncreaseStockDTO {
    @NotNull(message = "Increase operator must not be Null")
    @NotBlank(message = "Increase Operator  must not be Blank")
    private String increase;

}
