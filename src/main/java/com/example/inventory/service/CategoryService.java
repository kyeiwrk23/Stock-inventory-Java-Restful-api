package com.example.inventory.service;

import com.example.inventory.dto.request.CategoryRequestDTO;
import com.example.inventory.dto.response.CategoryResponseDTO;

public interface CategoryService {
    CategoryResponseDTO getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryRequestDTO createProduct(CategoryRequestDTO categoryRequestDTO);

    CategoryRequestDTO getCategory(Long categoryId);

    CategoryRequestDTO updateCategory(Long categoryId, CategoryRequestDTO categoryRequestDTO);

    String deleteCategory(Long categoryId);
}
