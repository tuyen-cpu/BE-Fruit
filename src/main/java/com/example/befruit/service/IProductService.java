package com.example.befruit.service;

import com.example.befruit.dto.BestSellingProduct;
import com.example.befruit.dto.request.ProductRequest;
import com.example.befruit.dto.response.ProductResponse;
import com.example.befruit.entity.Product;
import com.example.befruit.repo.specs.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
	Page<ProductResponse> getAllByCategoryId(Long id, Long price,Integer status, Integer page, Integer size);
	Page<ProductResponse> getAllByCategorySlug(String slug, Long price,Integer status, Integer page, Integer size);
	Page<ProductResponse> getAll(Long price, Integer status,Integer page, Integer size);

	Page<ProductResponse> search(String key, Integer page, Integer size);
	ProductResponse getById(Long id);
	Product getProductById(Long id);
	ProductResponse getBySlug(String slug);
	ProductResponse add(ProductRequest productRequest);
	List<Product> add(List<Product> products);
	ProductResponse edit(ProductRequest productRequest);
Page<ProductResponse> filter(ProductSpecification productSpecification, int page, int size);

	Page<BestSellingProduct> getBestSellingInMonth(Integer month);
}
