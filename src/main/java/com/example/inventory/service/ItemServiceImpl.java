package com.example.inventory.service;

import com.example.inventory.dto.request.AddItemRequestDTO;
import com.example.inventory.dto.response.AddItemResponseDTO;
import com.example.inventory.dto.response.ItemResponseDTOPagination;
import com.example.inventory.dto.request.ItemRequestDTO;
import com.example.inventory.exception.MyResourceNotFoundException;
import com.example.inventory.exception.ResourceExistException;
import com.example.inventory.exception.ResourceNotFoundMessageOnlyException;
import com.example.inventory.model.Category;
import com.example.inventory.model.InventoryItem;
import com.example.inventory.model.Supplier;
import com.example.inventory.repository.CategoryRepository;
import com.example.inventory.repository.ItemRepository;


import com.example.inventory.repository.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    public static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public ItemResponseDTOPagination getAllItems(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sortByOrder);


        Page<InventoryItem> itemsFromDB = itemRepository.findAll(pageable);


        if(itemsFromDB.isEmpty())
        {
            throw new ResourceNotFoundMessageOnlyException("Items");
        }

        ItemResponseDTOPagination itemResponseDTO = new ItemResponseDTOPagination();
        List<ItemRequestDTO> itemRequest = new ArrayList<>();
        List<ItemRequestDTO> itemResponse = itemsFromDB.getContent()
                .stream()
                .map(item ->modelMapper.map(item, ItemRequestDTO.class)
                ).toList();

        itemResponseDTO.setContent(itemResponse);
        itemResponseDTO.setPageNumber(itemsFromDB.getNumber());
        itemResponseDTO.setPageSize(itemsFromDB.getSize());
        itemResponseDTO.setTotalPages(itemsFromDB.getTotalPages());
        itemResponseDTO.setTotalElements(itemsFromDB.getTotalElements());
        itemResponseDTO.setLastPage(itemsFromDB.isLast());


        return itemResponseDTO;
    }

    @Override
    public ItemRequestDTO getItemById(Long itemId) {
        InventoryItem inventoryItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new MyResourceNotFoundException("Item","ItemId",itemId));

        ItemRequestDTO itemRequestDTO = modelMapper.map(inventoryItem, ItemRequestDTO.class);

        return itemRequestDTO;
    }

    @Override
    public ItemRequestDTO updateItem(Long itemId, ItemRequestDTO itemRequestDTO) {
        InventoryItem inventItemFromDB = itemRepository.findById(itemId)
                .orElseThrow(() -> new MyResourceNotFoundException("Item","ItemId",itemId));



        itemRequestDTO.setItemId(itemId);
        InventoryItem newInventoryItem = modelMapper.map(itemRequestDTO, InventoryItem.class);

        if (itemRequestDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(itemRequestDTO.getCategoryId())
                    .orElseThrow(() -> new MyResourceNotFoundException("Category","CategoryId",itemRequestDTO.getCategoryId()));
            newInventoryItem.setCategory(category);
        } else
        {
            newInventoryItem.setCategory(inventItemFromDB.getCategory());
        }


        newInventoryItem.setCategory(inventItemFromDB.getCategory());

        InventoryItem savedInventoryItem = itemRepository.save(newInventoryItem);

        return modelMapper.map(savedInventoryItem, ItemRequestDTO.class);
    }

    @Override
    public String deleteItem(Long itemId) {
        InventoryItem inventoryItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new MyResourceNotFoundException("Item","ItemId",itemId));
        itemRepository.delete(inventoryItem);
        return String.format("\'%s\' Deleted Successfully !!",inventoryItem.getItemName() );
    }

    @Override
    public AddItemResponseDTO addItem(Long supplierId, AddItemRequestDTO addItemRequestDTO) {
        Optional<InventoryItem> savedItemsFromDB = itemRepository.findBySupplierIdAndItemName(supplierId,addItemRequestDTO.getItemName());

        if(savedItemsFromDB.isPresent())
        {
            throw new MyResourceNotFoundException("Items","Item Name",savedItemsFromDB.get().getItemName());
        }


        InventoryItem inventoryItem = modelMapper.map(addItemRequestDTO, InventoryItem.class);

        Category category = categoryRepository.findById(addItemRequestDTO.getCategoryId())
                .orElseThrow(() -> new MyResourceNotFoundException("Category","CategoryId",addItemRequestDTO.getCategoryId()));

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new MyResourceNotFoundException("Supplier","SupplierId",supplierId));

        inventoryItem.setCategory(category);
        inventoryItem.setSupplier(supplier);

        InventoryItem savedInventoryItem = itemRepository.save(inventoryItem);


        return modelMapper.map(savedInventoryItem, AddItemResponseDTO.class);
    }

    @Override
    public AddItemResponseDTO increaseAndDecreaseStock(Long itemId, int tag) {
        InventoryItem inventoryItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new MyResourceNotFoundException("Item","ItemId",itemId));



        if(tag == 1){
            inventoryItem.setStock(inventoryItem.getStock() + tag);
        }else if(tag == -1 && inventoryItem.getStock() == 0){
            inventoryItem.setStock(0);
        }else {
            inventoryItem.setStock(inventoryItem.getStock() + tag);
        }

        InventoryItem savedInventoryItem = itemRepository.save(inventoryItem);

        return modelMapper.map(savedInventoryItem, AddItemResponseDTO.class);
    }

    @Override
    public AddItemResponseDTO getStock(Long itemId) {
        InventoryItem inventoryItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new  MyResourceNotFoundException("Item","ItemId",itemId));

        return modelMapper.map(inventoryItem, AddItemResponseDTO.class);
    }

    @Override
    public List<AddItemResponseDTO> filerByNameAndCategory(String itemName, String categoryName) {
        List<InventoryItem> items = itemRepository.findByItemNameAndCategoryName(itemName,categoryName);
        if(items.isEmpty())
        {
            throw new  MyResourceNotFoundException("Item","Item Name",itemName);
        }


        List<AddItemResponseDTO>  addItemResponseDTO = items.stream()
                .map(item->modelMapper.map(item,AddItemResponseDTO.class)).toList();
        return addItemResponseDTO;
    }

    @Override
    public List<AddItemResponseDTO> stockThreshold() {
       List<InventoryItem> items = itemRepository.findLowStock();

       if(items.isEmpty())
       {
           throw new ResourceNotFoundMessageOnlyException("Items below Stock threshold");
       }

       List<AddItemResponseDTO> itemResponse = items.stream()
               .map(item->modelMapper.map(item,AddItemResponseDTO.class)).toList();
        return itemResponse;
    }

    @Override
    public List<AddItemResponseDTO> OutOfStock() {
        List<InventoryItem> savedItems = itemRepository.findByStockEquals(0);
        if(savedItems.isEmpty()){
            throw new ResourceNotFoundMessageOnlyException("Items out of Stock");
        }

        List<AddItemResponseDTO> itemResponse = savedItems.stream()
                .map(item->modelMapper.map(item,AddItemResponseDTO.class)).toList();

        return itemResponse;
    }

    @Override
    public String setStatus() {
        List<InventoryItem> savedItems = itemRepository.findLowStock();
        if(savedItems.isEmpty())
        {
            throw new ResourceNotFoundMessageOnlyException("Items with Low Stock");
        }
        savedItems.forEach(item->{item.setStatus("Low Stock");});

        itemRepository.saveAll(savedItems);
        return "Status Set Successfully!!!";
    }


}
