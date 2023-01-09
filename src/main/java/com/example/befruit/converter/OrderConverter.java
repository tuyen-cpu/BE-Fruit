package com.example.befruit.converter;

import com.example.befruit.dto.response.OrderResponse;
import com.example.befruit.entity.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class OrderConverter {
	@Autowired
	private ModelMapper modelMapper;

	public OrderResponse convertToResponse(Order entity) {
		return modelMapper.map(entity, OrderResponse.class);
	}

	public Page<OrderResponse> convertToResponse(Page<Order> pageEntity) {
		if (pageEntity == null) {
			return null;
		}
		return pageEntity.map(this::convertToResponse);
	}
}
