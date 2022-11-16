package com.example.befruit.converter;

import com.example.befruit.dto.OrderDetailDTO;
import com.example.befruit.dto.response.OrderResponse;
import com.example.befruit.dto.response.ProductResponse;
import com.example.befruit.entity.Bill;
import com.example.befruit.entity.OrderDetail;
import com.example.befruit.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class OrderConverter {
	@Autowired
	private ModelMapper modelMapper;

	public OrderResponse convertToResponse(Bill entity) {


		return modelMapper.map(entity, OrderResponse.class);
	}

	public Page<OrderResponse> convertToResponse(Page<Bill> pageEntity) {
		if (pageEntity == null) {
			return null;
		}
		return pageEntity.map(this::convertToResponse);
	}
}
