package com.example.befruit.service.impl;

import com.example.befruit.converter.CategoryConverter;
import com.example.befruit.dto.CategoryDTO;
import com.example.befruit.repo.CategoryRepo;
import com.example.befruit.service.ICategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CategoryService implements ICategory {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private CategoryConverter categoryConverter;
    @Override
    public List<CategoryDTO> getAll() {
        return categoryRepo.findAll().stream().map(entity->categoryConverter.convertToDto(entity)).collect(Collectors.toList());
    }
}
