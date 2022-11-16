package com.example.befruit.dto.request;

import com.example.befruit.dto.response.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequest implements Serializable {
	private Long id;
	private Integer quantity;
	private Long productId;
	private Long userId;
}