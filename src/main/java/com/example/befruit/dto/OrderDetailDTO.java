package com.example.befruit.dto;

import com.example.befruit.dto.response.ProductResponse;
import com.example.befruit.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
	private Long id;
	private Long price;
	private Integer quantity;
	private Integer discount;
	private ProductResponse product;
	private Long billId;
}
