package com.example.befruit.service;

import com.example.befruit.dto.CategoryDTO;

import java.util.List;

public interface ICategory {
	List<CategoryDTO> getAll();

	List<CategoryDTO> getAll(Integer status);
}
