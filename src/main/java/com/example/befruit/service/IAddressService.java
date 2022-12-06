package com.example.befruit.service;

import com.example.befruit.dto.AddressDTO;
import com.example.befruit.entity.Address;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAddressService {
	Page<AddressDTO> getAllByUserId(Long price, Integer status, Integer page, Integer size);

	AddressDTO get(Long id);
	AddressDTO add(AddressDTO addressDTO);
	AddressDTO update(AddressDTO addressDTO);

	void delete(Long id);
	void delete(List<Long> ids);
}
