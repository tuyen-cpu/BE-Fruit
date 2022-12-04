package com.example.befruit.dto.response;

import com.example.befruit.dto.ImageDTO;
import com.example.befruit.entity.Category;
import com.example.befruit.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
	private Long id;
	private String name;
	private Long price;
	private String description;
	private Integer discount;
	private Integer quantity;
	private Integer status;
	private String slug;
	private CategoryResponse category;
	private ImageDTO image;
}
