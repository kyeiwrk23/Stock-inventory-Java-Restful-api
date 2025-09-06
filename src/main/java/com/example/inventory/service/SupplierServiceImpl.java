package com.example.inventory.service;


import com.example.inventory.dto.request.SupplierRequestDTO;
import com.example.inventory.dto.response.AddItemResponseDTO;
import com.example.inventory.dto.response.SupplierResponseDTO;
import com.example.inventory.dto.response.SupplierResponseDTOPagination;
import com.example.inventory.exception.MyResourceNotFoundException;
import com.example.inventory.exception.ResourceNotFoundMessageOnlyException;
import com.example.inventory.model.Supplier;
import com.example.inventory.repository.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;



@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public SupplierResponseDTOPagination getAllSuppliers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sortByOrder);

        Page<Supplier> savedSupplierFromDB = supplierRepository.findAll(pageable);

        if(savedSupplierFromDB.isEmpty()){
            throw new RuntimeException("Supplier doesn't exist!!!");
        }


        List<SupplierResponseDTO> supplierResponseDTOS= savedSupplierFromDB.getContent()
                .stream()
                .map(supplier->{
                    SupplierResponseDTO supplierResponseDTO = modelMapper.map(supplier, SupplierResponseDTO.class);
                    List<AddItemResponseDTO> addItemResponseDTO = supplier.getItems()
                            .stream()
                            .map(item->{
                                AddItemResponseDTO itemResponseDTO = modelMapper.map(item, AddItemResponseDTO.class);
                                return itemResponseDTO;
                            }).toList();
                    supplierResponseDTO.setItems(addItemResponseDTO);
                    return supplierResponseDTO;
                }).toList();

        SupplierResponseDTOPagination dtOPagination = new SupplierResponseDTOPagination();
        dtOPagination.setContents(supplierResponseDTOS);
        dtOPagination.setPageNumber(savedSupplierFromDB.getNumber());
        dtOPagination.setPageSize(savedSupplierFromDB.getSize());
        dtOPagination.setTotalPages(savedSupplierFromDB.getTotalPages());
        dtOPagination.setTotalElements(savedSupplierFromDB.getTotalElements());
        dtOPagination.setLastPage(savedSupplierFromDB.isLast());



        return dtOPagination;
    }

    @Override
    public SupplierResponseDTO createSupplier(SupplierRequestDTO supplierRequestDTO) {
        Supplier supplierFromDB = supplierRepository.findSupplierByTaxId(supplierRequestDTO.getTaxCode());

        if (supplierFromDB != null) {
            throw new ResourceNotFoundMessageOnlyException("Supplier");
        }

        supplierFromDB = modelMapper.map(supplierRequestDTO, Supplier.class);

        supplierFromDB.setCreatedAt(LocalDate.now());
        supplierFromDB.setLastUpdated(LocalDate.now());

        Supplier savedSupplier = supplierRepository.save(supplierFromDB);

        List<AddItemResponseDTO> items = savedSupplier.getItems()
                .stream()
                .map(inventories->modelMapper.map(inventories,AddItemResponseDTO.class)).toList();

        SupplierResponseDTO supplierResponseDTO = modelMapper.map(savedSupplier, SupplierResponseDTO.class);
        supplierResponseDTO.setItems(items);


        return supplierResponseDTO;
    }

    @Override
    public SupplierResponseDTO getSupplierById(Long supplierId) {
        Supplier savedSupplierFromDB =  supplierRepository.findById(supplierId)
                .orElseThrow(() -> new MyResourceNotFoundException("Supplier","supplierId",supplierId));

        return modelMapper.map(savedSupplierFromDB, SupplierResponseDTO.class);

    }

    @Override
    public SupplierResponseDTO updateSupply(SupplierRequestDTO supplierRequestDTO, Long supplierId) {
        Supplier supplierFromDB = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new MyResourceNotFoundException("Supplier","supplierId",supplierId));


        Supplier newSupplier = modelMapper.map(supplierRequestDTO, Supplier.class);
        newSupplier.setSupplierId(supplierId);
        newSupplier.setLastUpdated(LocalDate.now());
        newSupplier.setCreatedAt(supplierFromDB.getCreatedAt());
        Supplier updatedSupplier = supplierRepository.save(newSupplier);

        return modelMapper.map(updatedSupplier, SupplierResponseDTO.class);

    }

    @Override
    public String deleteSupplier(Long supplierId) {
        Supplier supplierFromDb = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new MyResourceNotFoundException("Supplier","supplierId",supplierId));

        supplierRepository.delete(supplierFromDb);

        return String.format("%s has been deleted", supplierFromDb.getSupplierName());
    }


}
