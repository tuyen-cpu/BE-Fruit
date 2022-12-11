package com.example.befruit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
	private Long id;
	private String name;
	private String slug;
	private Integer status;
}
