package com.example.befruit.service.impl;

import com.example.befruit.converter.CategoryConverter;
import com.example.befruit.dto.CategoryDTO;
import com.example.befruit.repo.CategoryRepo;
import com.example.befruit.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

}
