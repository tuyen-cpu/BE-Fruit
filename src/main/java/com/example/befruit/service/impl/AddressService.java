package com.example.befruit.service.impl;

import com.example.befruit.converter.AddressConverter;
import com.example.befruit.dto.AddressDTO;
import com.example.befruit.entity.Address;
import com.example.befruit.entity.User;
import com.example.befruit.repo.AddressRepo;
import com.example.befruit.repo.UserRepo;
import com.example.befruit.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService implements IAddressService {
	@Autowired
	private AddressConverter addressConverter;
	@Autowired
	private AddressRepo addressRepo;
	@Autowired
	private UserRepo userRepo;

	@Override
	public Page<AddressDTO> getAllByUserId(Long userId, Integer status, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
		return addressConverter.convertToDTO(addressRepo.findAllByUserIdAndStatus(userId, status, pageable));
	}

	@Override
	public AddressDTO get(Long id) {
		return addressConverter.convertToDTO(addressRepo.findById(id).get());
	}

	@Transactional
	@Override
	public AddressDTO add(AddressDTO addressDTO) {
		if(addressDTO.getIsDefault()==1){
		this.setDefaultOnlyOne();
		}
		return addressConverter.convertToDTO(addressRepo.save(addressConverter.convertToEntity(addressDTO)));
	}
 private void setDefaultOnlyOne(){
	 List<Address> addresses = addressRepo.findAllByIsDefault(1);
	 List<Address> addressesChanged = addresses.stream().peek(e -> e.setIsDefault(0)).collect(Collectors.toList());
	 addressRepo.saveAll(addressesChanged);
 }
 @Transactional
	@Override
	public AddressDTO update(AddressDTO addressDTO) {
		this. setDefaultOnlyOne();
		Address address = addressRepo.findById(addressDTO.getId()).get();
		address.setIsDefault(addressDTO.getIsDefault());
		address.setStatus(addressDTO.getStatus());
		address.setCity(addressDTO.getCity());
		address.setWard(addressDTO.getWard());
		address.setDistrict(addressDTO.getDistrict());
		address.setFirstName(addressDTO.getFirstName());
		address.setLastName(addressDTO.getLastName());
		address.setDescription(addressDTO.getDescription());
		User user = userRepo.findById(addressDTO.getUserId()).get();
		address.setUser(user);
		address.setStreet(addressDTO.getStreet());
		address.setPhone(addressDTO.getPhone());
		return addressConverter.convertToDTO(addressRepo.save(address));
	}

	@Override
	public void delete(Long id) {
		addressRepo.deleteById(id);
	}

}
