package com.example.inventory.service;

import com.example.inventory.dto.request.AddItemRequestDTO;
import com.example.inventory.dto.request.ItemRequestDTO;
import com.example.inventory.dto.response.AddItemResponseDTO;
import com.example.inventory.dto.response.ItemResponseDTOPagination;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemService {
    ItemResponseDTOPagination getAllItems(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ItemRequestDTO getItemById(Long itemId);

    ItemRequestDTO updateItem(Long itemId, ItemRequestDTO itemRequestDTO);

    String deleteItem(Long itemId);

    AddItemResponseDTO addItem(Long supplierId, AddItemRequestDTO addItemRequestDTO);


    AddItemResponseDTO increaseAndDecreaseStock(Long itemId, int i);

    AddItemResponseDTO getStock(Long itemId);

    List<AddItemResponseDTO> filerByNameAndCategory(String itemName, String categoryName);

    List<AddItemResponseDTO> stockThreshold();

    List<AddItemResponseDTO> OutOfStock();

    String setStatus();

//    List<AddItemResponseDTO> reOrderItems();
}
