package com.example.befruit.dto;

import com.example.befruit.dto.response.CategoryResponse;
import com.example.befruit.dto.response.ProductResponse;
import com.example.befruit.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BestSellingProduct {
	private Long id;
	private String name;
	private Long price;
	private Integer status;
	private Long quantity;

}
