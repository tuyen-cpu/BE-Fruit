package com.example.befruit.converter;

import com.example.befruit.dto.CategoryDTO;
import com.example.befruit.entity.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryConverter {
	@Autowired
	private ModelMapper modelMapper;

	public CategoryDTO convertToDto(Category entity) {
		return modelMapper.map(entity, CategoryDTO.class);
	}

	public Category convertToEntity(CategoryDTO dto) {
		return modelMapper.map(dto, Category.class);
	}
}
