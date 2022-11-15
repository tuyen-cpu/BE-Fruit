package com.example.befruit.service;

import com.example.befruit.dto.request.OrderRequest;
import com.example.befruit.dto.response.OrderResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrderService {
    Boolean addOrder(OrderRequest orderRequest);
    Page<OrderResponse> getByUserId(Long userId, Integer page, Integer size);
    OrderResponse getById(Long id);
}
