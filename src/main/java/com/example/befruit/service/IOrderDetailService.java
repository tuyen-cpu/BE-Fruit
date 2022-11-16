package com.example.befruit.service;

import com.example.befruit.dto.OrderDetailDTO;

import java.util.List;

public interface IOrderDetailService {
	List<OrderDetailDTO> getAllByOrderId(Long orderId);
}
