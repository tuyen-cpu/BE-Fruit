package com.example.befruit.service.impl;

import com.example.befruit.converter.ProductConverter;
import com.example.befruit.dto.request.ProductRequest;
import com.example.befruit.dto.response.ProductResponse;
import com.example.befruit.entity.Product;
import com.example.befruit.repo.ProductRepo;
import com.example.befruit.service.IProductService;
import com.example.befruit.service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class ProductService implements IProductService {
	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private ProductConverter productConverter;
@Autowired
private IStorageService storageService;
	@Override
	public Page<ProductResponse> getAllByCategoryId(Long id, Long price,Integer status, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size);
//		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
if(status==null){
	return productConverter.convertToResponse(productRepo.findAllByCategoryIdAndPriceLessThanEqual(id, price, pageable));

}
		return productConverter.convertToResponse(productRepo.findAllByCategoryIdAndPriceLessThanEqualAndStatus(id, price,status, pageable));

	}

	@Override
	public Page<ProductResponse> getAll(Long price,Integer status, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size);
//		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
		if(status==null){
			return productConverter.convertToResponse(productRepo.findAllByPriceLessThanEqual(price, pageable));
		}
		return productConverter.convertToResponse(productRepo.findAllByPriceLessThanEqualAndStatus(price,status, pageable));

	}

	@Override
	public Page<ProductResponse> search(String key, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
		return productConverter.convertToResponse(productRepo.findAllByNameOrCategoryName(key, pageable));
	}

	@Override
	public ProductResponse getById(Long id) {
		try {
			return productConverter.convertToResponse(productRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException("Product "+id+" does not exist!")));
		} catch (Exception e) {
			throw new RuntimeException("Product " + id + " is not found!");
		}

	}

	@Override
	public ProductResponse getBySlug(String slug) {
		return productConverter.convertToResponse(productRepo.findBySlug(slug));
	}

	@Override

public ProductResponse add(ProductRequest productRequest) {
	try {
		Product product = productConverter.convertToEntity(productRequest);
		Product productAdded =productRepo.save(product);
		return productConverter.convertToResponse(productAdded);
	} catch (Exception e) {
		throw new RuntimeException("Product failed!");
	}
}
}
