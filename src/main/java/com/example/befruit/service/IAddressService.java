package com.example.befruit.service;

import com.example.befruit.dto.AddressDTO;
import org.springframework.data.domain.Page;

public interface IAddressService {
	Page<AddressDTO> getAllByUserId(Long price, Integer status, Integer page, Integer size);

	AddressDTO get(Long id);
	AddressDTO add(AddressDTO addressDTO);
}
