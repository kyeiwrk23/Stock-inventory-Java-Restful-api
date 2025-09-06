package com.example.inventory.service;

import com.example.inventory.config.AppConfig;
import com.example.inventory.dto.request.CategoryRequestDTO;
import com.example.inventory.dto.response.CategoryResponseDTO;
import com.example.inventory.exception.MyResourceNotFoundException;
import com.example.inventory.exception.ResourceExistException;
import com.example.inventory.exception.ResourceNotFoundMessageOnlyException;
import com.example.inventory.model.Category;
import com.example.inventory.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public CategoryResponseDTO getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sortByOrder);
        Page<Category> category = categoryRepository.findAll(pageable);

        if(category.isEmpty()){
            throw new ResourceNotFoundMessageOnlyException("Category");
        }

        List<Category> contentCategory = category.getContent();
        List<CategoryRequestDTO> categoryRequest = contentCategory.stream()
                .map(cat-> modelMapper.map(cat,CategoryRequestDTO.class)).toList();

        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setContent(categoryRequest);
        categoryResponseDTO.setPageNumber(category.getNumber());
        categoryResponseDTO.setPageSize(category.getSize());
        categoryResponseDTO.setTotalElements(category.getTotalElements());
        categoryResponseDTO.setTotalPages(category.getTotalPages());
        categoryResponseDTO.setLastPage(category.isLast());

        return categoryResponseDTO;
    }

    @Override
    public CategoryRequestDTO createProduct(CategoryRequestDTO categoryRequestDTO) {
        if(categoryRepository.existsByCategoryNameIgnoreCase(categoryRequestDTO.getCategoryName())) {
            throw new ResourceExistException("Category");
        }

        Category category = new Category();
        if(categoryRequestDTO.getParentCategory() == null) {
            categoryRequestDTO.setParentCategory("None");
        }

        Category newCategory = modelMapper.map(categoryRequestDTO,Category.class);

        Category savedCategory = categoryRepository.save(newCategory);
        categoryRequestDTO.setCategoryId(savedCategory.getCategoryId());

        return modelMapper.map(savedCategory,CategoryRequestDTO.class);
    }

    @Override
    public CategoryRequestDTO getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new MyResourceNotFoundException("Category","CategoryId",categoryId));

        return modelMapper.map(category,CategoryRequestDTO.class);
    }

    @Override
    public CategoryRequestDTO updateCategory(Long categoryId, CategoryRequestDTO categoryRequestDTO) {
        Category savedCategoryFromDB = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new MyResourceNotFoundException("Category","CategoryId",categoryId));

        categoryRequestDTO.setCategoryId(categoryId);
        Category category = modelMapper.map(categoryRequestDTO,Category.class);

        Category newCategory = categoryRepository.save(category);

        CategoryRequestDTO categoryRequest = modelMapper.map(newCategory, CategoryRequestDTO.class);

        return modelMapper.map(newCategory,CategoryRequestDTO.class);

    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new MyResourceNotFoundException("Category","CategoryId",categoryId));
        categoryRepository.delete(category);
        return "Deleted Successfully !!!";
    }


}
