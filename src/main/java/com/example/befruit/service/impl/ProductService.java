package com.example.befruit.service.impl;

import com.example.befruit.converter.ProductConverter;
import com.example.befruit.dto.response.ProductResponse;
import com.example.befruit.entity.Product;
import com.example.befruit.repo.ProductRepo;
import com.example.befruit.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {
	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private ProductConverter productConverter;

	@Override
	public Page<ProductResponse> getAllByCategoryId(Long id, Long price, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size);
//		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
		Page<Product> products = productRepo.findAllByCategoryIdAndPriceLessThanEqual(id, price, pageable);
		return productConverter.convertToResponse(products);
	}

	@Override
	public Page<ProductResponse> getAll(Long price, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size);
//		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
		return productConverter.convertToResponse(productRepo.findAllByPriceLessThanEqual(price, pageable));
	}

	@Override
	public Page<ProductResponse> search(String key, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
		return productConverter.convertToResponse(productRepo.findAllByNameOrCategoryName(key, pageable));
	}

	@Override
	public ProductResponse getById(Long id) {
		try {
			return productConverter.convertToResponse(productRepo.findById(id).get());
		} catch (Exception e) {
			throw new RuntimeException("Product " + id + " is not found!");
		}

	}

}
