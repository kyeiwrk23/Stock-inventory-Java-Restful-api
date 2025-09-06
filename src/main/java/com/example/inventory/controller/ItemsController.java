package com.example.inventory.controller;

import com.example.inventory.config.AppConstants;
import com.example.inventory.dto.request.AddItemRequestDTO;
import com.example.inventory.dto.request.DecreaseStockDTO;
import com.example.inventory.dto.request.IncreaseStockDTO;
import com.example.inventory.dto.request.ItemRequestDTO;
import com.example.inventory.dto.response.AddItemResponseDTO;
import com.example.inventory.dto.response.ItemResponseDTOPagination;
import com.example.inventory.service.ItemServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class ItemsController {

    @Autowired
    private ItemServiceImpl itemService;


    @GetMapping("/items")
    public ResponseEntity<ItemResponseDTOPagination> getAllItems(
            @RequestParam(name = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_ITEM_ID, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER,required = false) String sortOrder)
    {
        ItemResponseDTOPagination itemResponseDTO = itemService.getAllItems(pageNumber,pageSize,sortBy,sortOrder);

        return ResponseEntity.ok(itemResponseDTO);
    }

    @GetMapping("/items/{itemId}")
    public ResponseEntity<ItemRequestDTO> getItemById(@PathVariable Long itemId){
        ItemRequestDTO itemRequestDTO = itemService.getItemById(itemId);
        return  ResponseEntity.ok(itemRequestDTO);
    }

    @PostMapping("/supplier/{supplierId}/items")
    public ResponseEntity<AddItemResponseDTO> addItem(@Valid @PathVariable Long supplierId, @Valid @RequestBody AddItemRequestDTO AddItemRequestDTO){
        AddItemResponseDTO itemResponse = itemService.addItem(supplierId,AddItemRequestDTO);

        return new  ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }


    @PutMapping("/items/{itemId}")
    public ResponseEntity<ItemRequestDTO> updateItem(@PathVariable Long itemId, @Valid @RequestBody ItemRequestDTO itemRequestDTO){
        ItemRequestDTO itemResponse = itemService.updateItem(itemId,itemRequestDTO);
        return  ResponseEntity.ok(itemResponse);

    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable Long itemId){
        String itemResponse = itemService.deleteItem(itemId);
        return ResponseEntity.status(HttpStatus.OK).body(itemResponse);
    }

    /** Stock Management Endpoints
     * Increase and decrease Stock
     */

    @PostMapping("/items/{itemId}/stock/increase")
    public ResponseEntity<AddItemResponseDTO> increaseStock(@PathVariable Long itemId,@Valid @RequestBody IncreaseStockDTO operation){
        AddItemResponseDTO addItemResponseDTO = itemService.increaseAndDecreaseStock(itemId,operation.getIncrease().equalsIgnoreCase("+")?1:0);

        return  ResponseEntity.ok(addItemResponseDTO);
    }

    @PostMapping("/items/{itemId}/stock/decrease")
    public ResponseEntity<AddItemResponseDTO> decreaseStock(@PathVariable Long itemId,@Valid @RequestBody DecreaseStockDTO operation){
        AddItemResponseDTO reduceItemResponseDTO = itemService.increaseAndDecreaseStock(itemId,operation.getDecrease().equalsIgnoreCase("-")?-1:0);

        return  ResponseEntity.ok(reduceItemResponseDTO);
    }

    @GetMapping("/items/{itemId}/stock")
    public ResponseEntity<AddItemResponseDTO> getStock(@PathVariable Long itemId){
        AddItemResponseDTO addItemResponseDTO = itemService.getStock(itemId);
        return  ResponseEntity.ok(addItemResponseDTO);
    }

    @GetMapping("/items/category")
    public ResponseEntity<List<AddItemResponseDTO>> filterByNameAndCategory(
            @RequestParam String itemName,@RequestParam String categoryName) {
        List<AddItemResponseDTO> addItemResponseDTOS = itemService.filerByNameAndCategory(itemName,categoryName);
        return  ResponseEntity.ok(addItemResponseDTOS);
    }

    @GetMapping("/items/lowstock")
    public ResponseEntity<List<AddItemResponseDTO>> filterByLowStock(){
        List<AddItemResponseDTO> lowStock = itemService.stockThreshold();
        return  ResponseEntity.ok(lowStock);
    }

    @GetMapping("/items/out-of-stock")
    public ResponseEntity<List<AddItemResponseDTO>> filterOutOfStock(){
        List<AddItemResponseDTO> lowStock = itemService.OutOfStock();
        return  ResponseEntity.ok(lowStock);
    }

    @PostMapping("/items/setstatus")
    public ResponseEntity<String> filterSetStatus(){
        String status = itemService.setStatus();
        return  ResponseEntity.ok(status);
    }
}
