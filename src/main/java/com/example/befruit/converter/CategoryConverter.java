package com.example.befruit.converter;

import com.example.befruit.dto.CategoryDTO;
import com.example.befruit.dto.response.ProductResponse;
import com.example.befruit.entity.Category;
import com.example.befruit.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
	public Page<CategoryDTO> convertToDTO(Page<Category> pageEntity) {
		if (pageEntity == null) {
			return null;
		}
		return pageEntity.map(this::convertToDto);
	}
}
