package com.example.befruit.service;

import com.example.befruit.dto.CategoryDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoryService {
	Page<CategoryDTO> getAll(Integer status,Integer page,Integer size);
}
