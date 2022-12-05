package com.example.befruit.controller.admin;

import com.example.befruit.dto.CategoryDTO;
import com.example.befruit.entity.ResponseObject;
import com.example.befruit.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/category")
public class CategoryManagerController {
	@Autowired
	private ICategoryService categoryService;

//	@GetMapping("/all")
//	public ResponseEntity<ResponseObject> getAll( @RequestParam(name = "page", defaultValue = "0") int page,
//																								@RequestParam(name = "size", defaultValue = "20") int size) {
//		try {
//			Page<> categoryDTOS =  categoryService.getAll(null, page, size);
//			return ResponseEntity.ok().body(new ResponseObject("ok", "Get product successful!", categoryDTOS));
//
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
//		}
//	}
}
