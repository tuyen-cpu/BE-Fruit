package com.example.befruit.service.impl;

import com.example.befruit.converter.CategoryConverter;
import com.example.befruit.dto.CategoryDTO;
import com.example.befruit.dto.response.CategoryResponse;
import com.example.befruit.entity.Category;
import com.example.befruit.entity.Product;
import com.example.befruit.repo.CategoryRepo;
import com.example.befruit.service.ICategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class CategoryService implements ICategoryService {
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private CategoryConverter categoryConverter;



	@Override
	public Page<CategoryDTO> getAll(Integer status,Integer page,Integer size) {
		Pageable pageable = PageRequest.of(page, size);
		if(status==null){
			return categoryConverter.convertToDTO(categoryRepo.findAll(pageable));
		}
		return categoryConverter.convertToDTO(categoryRepo.findAllByStatus(status,pageable));

	}

	@Override
	public CategoryDTO add(CategoryDTO categoryDTO) {
		try {

			Category category = categoryConverter.convertToEntity(categoryDTO);
			Category categoryAdded = categoryRepo.save(category);
			return categoryConverter.convertToDto(categoryAdded);
		} catch (Exception e) {
			throw new RuntimeException("Category add failed!");
		}
	}

	@Override
	public CategoryDTO edit(CategoryDTO categoryDTO) {
		try {
			Category category = categoryRepo.findById(categoryDTO.getId())
					.orElseThrow(() -> new EntityNotFoundException("Category " + categoryDTO.getId() + " does not exist!"));
			BeanUtils.copyProperties(categoryDTO, category, "id");
			Category categoryAdded = categoryRepo.save(category);
			return categoryConverter.convertToDto(categoryAdded);
		} catch (Exception e) {
			throw new RuntimeException("Category edit failed!");
		}
	}

}
