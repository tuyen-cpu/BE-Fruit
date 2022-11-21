package com.example.befruit.dto.request;

import com.example.befruit.dto.AddressDTO;
import com.example.befruit.dto.OrderDetailDTO;
import com.example.befruit.entity.OrderDetail;
import com.example.befruit.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest implements Serializable {
	private Long id;
	private Long shippingCost;
	private String description;
	private String address;
	private Long userId;
	private List<OrderDetailRequest> orderDetails = new ArrayList<>();
	private Payment payment;
}
