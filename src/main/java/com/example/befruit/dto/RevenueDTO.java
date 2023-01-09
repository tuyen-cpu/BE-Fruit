package com.example.befruit.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RevenueDTO  {
	private Object createdAt;
	private Integer totalOrder;
	private Long grossRevenue;
	private Long shipping;
	private Long netRevenue;

}
