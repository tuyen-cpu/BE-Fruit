package com.example.befruit.dto.request;

import com.example.befruit.dto.response.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRequest {
	private Long id;
	private Long price;
	private Integer quantity;
	private Integer discount;
	private Long productId;
	private Long billId;
}
