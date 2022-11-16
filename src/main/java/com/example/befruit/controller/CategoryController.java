package com.example.befruit.controller;

import com.example.befruit.dto.CategoryDTO;
import com.example.befruit.entity.EStatus;
import com.example.befruit.entity.ResponseObject;
import com.example.befruit.service.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/all")
	public ResponseEntity<ResponseObject> getAll() {
		try {

			List<CategoryDTO> categoryDTOS = categoryService.getAll(EStatus.ACTIVE.getName());
			return ResponseEntity.ok().body(new ResponseObject("ok", "Get image successful!", categoryDTOS));

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));

		}
	}

}
