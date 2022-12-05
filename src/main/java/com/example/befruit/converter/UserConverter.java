package com.example.befruit.converter;

import com.example.befruit.dto.UserDTO;
import com.example.befruit.dto.response.ProductResponse;
import com.example.befruit.dto.response.UserResponse;
import com.example.befruit.entity.Product;
import com.example.befruit.entity.Role;
import com.example.befruit.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
	public Page<UserDTO> convertToDTO(Page<User> pageEntity) {
		if (pageEntity == null) {
			return null;
		}
		return pageEntity.map(this::convertToDto);
	}
	public UserResponse convertToResponse(User entity) {
		UserResponse userResponse=modelMapper.map(entity, UserResponse.class);
		List<String> role =entity.getRoles().stream().map(Role::getName).collect(Collectors.toList());
		userResponse.setRoles(role);
		return userResponse;
	}

	public User convertToEntity(UserResponse response) {
		return modelMapper.map(response, User.class);
	}
	public Page<UserResponse> convertToResponse(Page<User> pageEntity) {
		if (pageEntity == null) {
			return null;
		}
		return pageEntity.map(this::convertToResponse);
	}
}
