package com.example.inventory.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DecreaseStockDTO {

    @NotNull(message = "Decrease operator must not be Null")
    @NotBlank(message = "Decrease Operator  must not be Blank")
    private String decrease;
}
