package com.example.befruit.dto;

import com.example.befruit.dto.response.ProductResponse;
import com.example.befruit.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter


public class BestSellingProduct {
	private String name;
	private Integer quantity;

	public BestSellingProduct(String name, Integer quantity) {
		this.name = name;
		this.quantity = quantity;
	}
}
