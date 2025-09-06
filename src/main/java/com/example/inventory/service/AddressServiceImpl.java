package com.example.inventory.service;

import com.example.inventory.dto.request.AddressRequestDTO;
import com.example.inventory.dto.request.AddressRequestDTOPagination;
import com.example.inventory.dto.response.AddressResponseDTO;
import com.example.inventory.exception.MyResourceNotFoundException;
import com.example.inventory.exception.ResourceExistException;
import com.example.inventory.exception.ResourceNotFoundMessageOnlyException;
import com.example.inventory.model.Address;
import com.example.inventory.model.Supplier;
import com.example.inventory.repository.AddressRepository;
import com.example.inventory.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AddressResponseDTO addAddress(AddressRequestDTO addressRequestDTO) {
        Supplier savedSupplierDB = supplierRepository.findById(addressRequestDTO.getSupplierId())
                .orElseThrow(() -> new MyResourceNotFoundException("Address","AddressId",addressRequestDTO.getSupplierId()));

        List<Address> savedAddressDB = savedSupplierDB.getAddress();

        savedAddressDB.forEach(address -> {
            if(address.getStreetName().equalsIgnoreCase(addressRequestDTO.getStreetName())
            && address.getCity().equalsIgnoreCase(addressRequestDTO.getCity()) &&
                    address.getState().equalsIgnoreCase(addressRequestDTO.getState())
            && address.getZip().equalsIgnoreCase(addressRequestDTO.getZip()) &&
                    address.getCountry().equalsIgnoreCase(addressRequestDTO.getCountry())) {
                throw new ResourceExistException("Address");
            }
        });


        Address addressRequest = modelMapper.map(addressRequestDTO, Address.class);
        addressRequest.setSupplier(savedSupplierDB);

        Address savedAddress = addressRepository.save(addressRequest);

        return modelMapper.map(savedAddress, AddressResponseDTO.class);

    }

    @Override
    public AddressRequestDTOPagination getAllAddress(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable  pageable = PageRequest.of(pageNumber, pageSize, sortByOrder);

        Page<Address> addressPage = addressRepository.findAll(pageable);

        if(addressPage.isEmpty())
        {
            throw new ResourceNotFoundMessageOnlyException("Address");
        }
        //AddressResponseDTO
        List<AddressResponseDTO> addressResponseDTOs = addressPage.getContent().stream()
                .map(request->{
                    AddressResponseDTO addressResponseDTO = modelMapper.map(request, AddressResponseDTO.class);
                    addressResponseDTO.setSupplier(request.getSupplier());
                    return addressResponseDTO;
                }).toList();

        AddressRequestDTOPagination addressRequestDTOPagination = new AddressRequestDTOPagination();
        addressRequestDTOPagination.setContent(addressResponseDTOs);
        addressRequestDTOPagination.setPageNumber(addressPage.getNumber());
        addressRequestDTOPagination.setPageSize(addressPage.getSize());
        addressRequestDTOPagination.setTotalPages(addressPage.getTotalPages());
        addressRequestDTOPagination.setTotalElements(addressPage.getTotalElements());
        addressRequestDTOPagination.setLastPage(addressPage.isLast());

        return addressRequestDTOPagination;
    }

    @Override
    public AddressResponseDTO getAddress(Long addressId) {
        Address addressDto = addressRepository.findById(addressId)
                .orElseThrow(() -> new MyResourceNotFoundException("Address","AddressId",addressId));

        AddressResponseDTO  addressResponseDTO = modelMapper.map(addressDto, AddressResponseDTO.class);
        addressResponseDTO.setSupplier(addressDto.getSupplier());

        return addressResponseDTO;
    }

    @Override
    public List<AddressResponseDTO> getSupplierAddresses(Long supplierId) {

        List<Address> address = addressRepository.findAddressBySupplier(supplierId);

        if(address.isEmpty()){
            throw new ResourceNotFoundMessageOnlyException("Address");
        }


        List<Address> savedAddressDB = addressRepository.findAddressBySupplier(supplierId);

        List<AddressResponseDTO> addressResponseDTOs = savedAddressDB.stream()
                .map(request->{
                    AddressResponseDTO addressResponseDTO = modelMapper.map(request, AddressResponseDTO.class);
                    addressResponseDTO.setSupplier(request.getSupplier());
                    return addressResponseDTO;
                }).toList();

        return addressResponseDTOs;
    }

    @Override
    public AddressResponseDTO updateAddress(AddressRequestDTO addressRequestDTO, Long addressId) {
       Address addressSaved = addressRepository.findById(addressId)
                .orElseThrow(() -> new MyResourceNotFoundException("Address","AddressId",addressId));

       Address updated =  modelMapper.map(addressRequestDTO, Address.class);
       updated.setAddressId(addressId);
       updated.setSupplier(addressSaved.getSupplier());

       Address savedAddress = addressRepository.save(updated);

       AddressResponseDTO addressResponseDTO = modelMapper.map(savedAddress, AddressResponseDTO.class);
       addressResponseDTO.setAddressId(addressId);

       return addressResponseDTO;
    }

    @Override
    @Transactional
    public String deleteAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new MyResourceNotFoundException("Address","AddressId",addressId));
        addressRepository.delete(address);
        return "Successfully deleted address";
    }


}
