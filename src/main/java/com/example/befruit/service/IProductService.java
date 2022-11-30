package com.example.befruit.service;

import com.example.befruit.dto.request.ProductRequest;
import com.example.befruit.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

public interface IProductService {
	Page<ProductResponse> getAllByCategoryId(Long id, Long price,Integer status, Integer page, Integer size);

	Page<ProductResponse> getAll(Long price, Integer status,Integer page, Integer size);

	Page<ProductResponse> search(String key, Integer page, Integer size);
	ProductResponse getById(Long id);
	ProductResponse add(ProductRequest productRequest);

}
