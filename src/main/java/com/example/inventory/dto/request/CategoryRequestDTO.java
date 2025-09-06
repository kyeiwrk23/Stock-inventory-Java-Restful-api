package com.example.inventory.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryRequestDTO {
    private Long categoryId;

    @NotBlank(message = "Category Name must not be blank")
    @NotNull(message = "Category Name must not be Null")
    private String categoryName;

    @NotBlank(message = "Category Description must not be blank")
    @NotNull(message = "Category Description must not be Null")
    @Size(min = 7, message = "Category Description must be more the 7 characters")
    private String description;

    @NotBlank(message = "Parent Category  must not be blank")
    @NotNull(message = "Parent Category  must not be Null")
    private String parentCategory;
    private boolean Active;
}
