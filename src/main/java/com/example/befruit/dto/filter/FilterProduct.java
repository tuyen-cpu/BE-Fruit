package com.example.befruit.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterProduct {
	private Integer status;
	private String name;
	private Long price;
	private Integer categoryId;
	private List<Date> createdAt;
	private Integer page;
	private Integer size;


}
