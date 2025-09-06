package com.example.inventory.controller;

import com.example.inventory.config.AppConstants;
import com.example.inventory.dto.request.CategoryRequestDTO;
import com.example.inventory.dto.response.CategoryResponseDTO;
import com.example.inventory.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<CategoryResponseDTO> getAllCategories(
            @RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) String pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) String pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_CATEGORY_ID,required = false) String sortBy,
            @RequestParam(name = "sortOrder",defaultValue = AppConstants.SORT_ORDER,required = false) String sortOrder
            )
    {
        Integer pageNumberInt = Integer.parseInt(pageNumber);
        Integer pageSizeInt = Integer.parseInt(pageSize);

        System.out.println("pageNumber:  "+pageNumber+" pageSize: "+pageSize+" sortBy: "+sortBy+" sortOrder: "+sortOrder );
        CategoryResponseDTO categoryDto = categoryService.getAllCategories(pageNumberInt,pageSizeInt, sortBy,sortOrder);

        return new ResponseEntity<>(categoryDto,HttpStatus.OK);

    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryRequestDTO> createCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO){
        CategoryRequestDTO categoryDto = categoryService.createProduct(categoryRequestDTO);
        return new ResponseEntity<>(categoryRequestDTO,HttpStatus.CREATED);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryRequestDTO> getCategory(@PathVariable("categoryId") Long categoryId){
        CategoryRequestDTO categoryRequestDTO =  categoryService.getCategory(categoryId);

        return ResponseEntity.status(HttpStatus.OK).body(categoryRequestDTO);
    }
    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryRequestDTO> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        CategoryRequestDTO categoryDTO = categoryService.updateCategory(categoryId,categoryRequestDTO);

        return  ResponseEntity.status(HttpStatus.OK).body(categoryDTO);
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        var status = categoryService.deleteCategory(categoryId);

        return new ResponseEntity<>(status,HttpStatus.OK);
    }
}
