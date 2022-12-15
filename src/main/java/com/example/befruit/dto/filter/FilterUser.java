package com.example.befruit.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterUser {
	private Integer status;
	private String role;
	private List<Date> createdAt;
	private String email;
	private Integer page;
	private Integer size;

}
