package com.example.befruit.controller.admin;

import com.example.befruit.dto.CategoryDTO;
import com.example.befruit.dto.ImageDTO;
import com.example.befruit.dto.request.ProductRequest;
import com.example.befruit.dto.response.CategoryResponse;
import com.example.befruit.dto.response.ProductResponse;
import com.example.befruit.entity.ResponseObject;
import com.example.befruit.service.ICategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/category")
public class CategoryManagerController {
	@Autowired
	private ICategoryService categoryService;

	@GetMapping("/all")
	public ResponseEntity<ResponseObject> getAll( @RequestParam(name = "page", defaultValue = "0") int page,
																								@RequestParam(name = "size", defaultValue = "20") int size) {
		try {
			Page<CategoryDTO> categoryDTOS =  categoryService.getAll(null, page, size);
			return ResponseEntity.ok().body(new ResponseObject("ok", "Get categories successful!", categoryDTOS));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
		}
	}
	@PostMapping(value = "/add")
	public ResponseEntity<ResponseObject> add(@RequestBody CategoryDTO categoryDTO) throws JsonProcessingException {
		try {


			CategoryDTO categoryResponse;
			if(categoryDTO.getId()!=null){
				categoryResponse = categoryService.edit(categoryDTO);
			}else{
				categoryResponse = categoryService.add(categoryDTO);
			}

			return ResponseEntity.ok().body(new ResponseObject("ok", "Update category successfully!", categoryResponse));

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));

		}

	}
}
