package com.example.befruit.dto.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class FilterUser {
	private Integer status;
	private String role;
	private List<Date> createdAt;
	private String email;
	private Integer page;
	private Integer size;

}
