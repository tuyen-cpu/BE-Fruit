package com.example.befruit.service;

import com.example.befruit.dto.request.OrderRequest;
import com.example.befruit.dto.response.OrderResponse;
import com.example.befruit.dto.response.ProductResponse;
import com.example.befruit.entity.Bill;
import com.example.befruit.entity.Product;
import com.example.befruit.entity.ShippingStatus;
import com.example.befruit.repo.specs.EntitySpecification;
import org.springframework.data.domain.Page;

public interface IOrderService {
	Boolean addOrder(OrderRequest orderRequest);

	Page<OrderResponse> getByUserId(Long userId, Integer page, Integer size);

	OrderResponse getById(Long id);
	OrderResponse updateStatusShipping(Long id,String status);
	Page<OrderResponse> getAll(Integer page, Integer size);
	Page<OrderResponse> filter(EntitySpecification<Bill> productSpecification, int page, int size);
	OrderResponse updateShippingStatus(Long id, ShippingStatus shippingStatus);
}
