package com.example.befruit.service;

import com.example.befruit.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

public interface IProductService {
    Page<ProductResponse> getAllByCategoryId(Long id,Long price,Integer page, Integer size);
}
