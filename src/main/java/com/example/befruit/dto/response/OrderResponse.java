package com.example.befruit.dto.response;


import com.example.befruit.entity.Address;
import com.example.befruit.entity.Payment;
import com.example.befruit.entity.ShippingStatus;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
	private Long id;
	private Long total;
	private Date createdDate;
	private ShippingStatus shippingStatus;
	private String address;
	private String fullName;
	private String phone;
	private Payment payment;
	private String createdBy;
}
