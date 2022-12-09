package com.example.befruit.service;

import com.example.befruit.dto.request.ProductRequest;
import com.example.befruit.dto.response.ProductResponse;
import com.example.befruit.entity.Product;
import com.example.befruit.entity.User;
import com.example.befruit.repo.specs.EntitySpecification;
import org.springframework.data.domain.Page;

public interface IProductService {
	Page<ProductResponse> getAllByCategoryId(Long id, Long price,Integer status, Integer page, Integer size);
	Page<ProductResponse> getAllByCategorySlug(String slug, Long price,Integer status, Integer page, Integer size);
	Page<ProductResponse> getAll(Long price, Integer status,Integer page, Integer size);

	Page<ProductResponse> search(String key, Integer page, Integer size);
	ProductResponse getById(Long id);
	ProductResponse getBySlug(String slug);
	ProductResponse add(ProductRequest productRequest);
Page<ProductResponse> filter(EntitySpecification<Product> productSpecification, int page, int size);

}
