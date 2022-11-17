package com.example.befruit.service.impl;

import com.example.befruit.converter.AddressConverter;
import com.example.befruit.dto.AddressDTO;
import com.example.befruit.repo.AddressRepo;
import com.example.befruit.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AddressService implements IAddressService {
	@Autowired
	private AddressConverter addressConverter;
	@Autowired
	private AddressRepo addressRepo;

	@Override
	public Page<AddressDTO> getAllByUserId(Long userId, Integer status, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
		return addressConverter.convertToDTO(addressRepo.findAllByUserIdAndStatus(userId, status, pageable));
	}

	@Override
	public AddressDTO get(Long id) {
		return addressConverter.convertToDTO(addressRepo.findById(id).get());
	}

	@Override
	public AddressDTO add(AddressDTO addressDTO) {
		return addressConverter.convertToDTO(addressRepo.save(addressConverter.convertToEntity(addressDTO)));
	}

}
