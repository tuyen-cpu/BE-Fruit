package com.example.befruit.dto.filter;

import com.example.befruit.entity.Payment;
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
public class OrderFilter {
	private List<Date> createdDate;
	private Long shippingStatusId;
	private String address;
	private Payment payment;
	private Integer page;
	private Integer size;
}
