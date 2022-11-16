package com.example.befruit.converter;

import com.example.befruit.dto.UserDTO;
import com.example.befruit.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserConverter {
	@Autowired

	private ModelMapper modelMapper;

	public UserDTO convertToDto(User entity) {
		return modelMapper.map(entity, UserDTO.class);
	}

	public User convertToEntity(UserDTO dto) {
		return modelMapper.map(dto, User.class);
	}
}
