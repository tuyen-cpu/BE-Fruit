package com.example.befruit.service;

import com.example.befruit.dto.request.CartItemRequest;
import com.example.befruit.dto.response.CartItemResponse;
import com.example.befruit.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

public interface ICartService {
    Page<CartItemResponse> getByUserId(Long userId,Integer page, Integer size);
    CartItemResponse add(CartItemRequest cartItemRequest);
    CartItemResponse update(CartItemRequest cartItemRequest);
    void delete(Long id);
    void deleteByUserId(Long userId);

}
