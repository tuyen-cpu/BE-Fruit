package com.example.befruit.service;

import com.example.befruit.dto.request.OrderRequest;

public interface IOrderService {
    Boolean addOrder(OrderRequest orderRequest);
}
