package com.example.befruit.converter;

import com.example.befruit.dto.AddressDTO;
import com.example.befruit.dto.response.ProductResponse;
import com.example.befruit.entity.Address;
import com.example.befruit.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class AddressConverter {
    @Autowired
    private ModelMapper modelMapper;

    public Address convertToEntity(AddressDTO addressDTO) {
        return modelMapper.map(addressDTO,Address.class);
    }
    public AddressDTO convertToDTO(Address address) {
        return modelMapper.map(address,AddressDTO.class);
    }
    public Page<AddressDTO> convertToDTO(Page<Address> pageEntity){
        if (pageEntity == null) {
            return null;
        }
        return pageEntity.map(this::convertToDTO);
    }
}
