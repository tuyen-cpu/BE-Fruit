package com.example.befruit.controller;

import com.example.befruit.dto.response.ProductResponse;
import com.example.befruit.entity.ResponseObject;
import com.example.befruit.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	@Autowired
	private ProductService productService;

	@GetMapping("/all")
	public ResponseEntity<ResponseObject> getAllByCategoryIdAndPrice(@RequestParam(name = "categoryId", required = false) long categoryId,
																																	 @RequestParam(name = "price", required = false) long price,
																																	 @RequestParam(name = "page", defaultValue = "0") int page,
																																	 @RequestParam(name = "size", defaultValue = "10") int size) {
		try {
			Page<ProductResponse> products = null;
			if (categoryId == 0) {
				products = productService.getAll(price, page, size);
			} else {
				System.out.println("có cate");
				products = productService.getAllByCategoryId(categoryId, price, page, size);
			}

			return ResponseEntity.ok().body(new ResponseObject("ok", "Get product successful!", products));

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));

		}
	}

	@GetMapping("/search")
	public ResponseEntity<ResponseObject> getAllByCategoryIdAndPrice(@RequestParam(name = "key", required = false) String key,
																																	 @RequestParam(name = "page", defaultValue = "0") int page,
																																	 @RequestParam(name = "size", defaultValue = "10") int size) {

		try {
			Page<ProductResponse> products = productService.search(key, page, size);
			return ResponseEntity.ok().body(new ResponseObject("ok", "Search with key " + key + " product successful!", products));

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));

		}
	}
}
