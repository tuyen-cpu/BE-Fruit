package com.example.befruit.converter;

import com.example.befruit.dto.ImageDTO;
import com.example.befruit.dto.OrderDetailDTO;
import com.example.befruit.dto.response.ProductResponse;
import com.example.befruit.entity.Image;
import com.example.befruit.entity.OrderDetail;
import com.example.befruit.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailConverter {
	@Autowired
	private ModelMapper modelMapper;

	public OrderDetail convertToEntity(OrderDetailDTO orderDetailDTO) {

		return modelMapper.map(orderDetailDTO, OrderDetail.class);
	}

	public OrderDetailDTO convertToResponse(OrderDetail orderDetail) {
		ImageDTO imageDTO = modelMapper.map(orderDetail.getProduct().getImages().get(0), ImageDTO.class);
		OrderDetailDTO result = modelMapper.map(orderDetail, OrderDetailDTO.class);
		result.getProduct().setImage(imageDTO);
		return result;
	}
}
