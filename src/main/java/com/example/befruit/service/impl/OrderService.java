package com.example.befruit.service.impl;

import com.example.befruit.converter.AddressConverter;
import com.example.befruit.converter.OrderConverter;
import com.example.befruit.converter.OrderDetailConverter;
import com.example.befruit.dto.AddressDTO;
import com.example.befruit.dto.request.OrderRequest;
import com.example.befruit.dto.response.OrderResponse;
import com.example.befruit.entity.*;
import com.example.befruit.repo.AddressRepo;
import com.example.befruit.repo.OrderDetailRepo;
import com.example.befruit.repo.OrderRepo;
import com.example.befruit.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
	@Autowired
	private AddressRepo addressRepo;
	@Autowired
	private OrderDetailRepo orderDetailRepo;
	@Autowired
	private AddressConverter addressConverter;
	@Autowired
	private OrderRepo orderRepo;
	@Autowired
	private OrderDetailConverter orderDetailConverter;
	@Autowired
	private OrderConverter orderConverter;

	@Autowired
	private ShippingStatusService shippingStatusService;

	@Transactional
	@Override
	public Boolean addOrder(OrderRequest orderRequest) {

		try {
			AddressDTO addressResponse = orderRequest.getAddress();
			Address address = null;
			if (addressResponse.getId() == null) {
				address = addressRepo.save(addressConverter.convertToEntity(addressResponse));
			} else {
				address = addressConverter.convertToEntity(addressResponse);
			}
			System.out.println(address.getDistrict());
			System.out.println(address.getCity());
			System.out.println(address.getWard());

			ShippingStatus shippingStatus = shippingStatusService.getByName(EShippingStatus.UNVERIFIED.getName());
			shippingStatus.setName(EShippingStatus.UNVERIFIED.getName());

			Bill order = new Bill();
			order.setStatus(EStatus.ACTIVE.getName());
			order.setShippingStatus(shippingStatus);
			order.setAddress(address);
			order.setDescription(orderRequest.getDescription());
			long total = 0L;

			List<OrderDetail> orderDetails = orderRequest.getOrderDetails().stream().map(dto -> orderDetailConverter.convertToEntity(dto)).collect(Collectors.toList());
			orderDetails.forEach(e -> {
				e.setBill(order);

			});
			for (OrderDetail orderDetail : orderDetails) {
				System.out.println(orderDetail.getQuantity() + "_" + orderDetail.getPrice());
				total += (orderDetail.getPrice() - orderDetail.getPrice() * orderDetail.getDiscount() / 100) * orderDetail.getQuantity();
			}
			order.setTotal(total);
			order.setOrderDetails(orderDetails);
			orderRepo.save(order);

			return true;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	@Override
	public Page<OrderResponse> getByUserId(Long userId, Integer page, Integer size) {
		try {
			Pageable pageable = PageRequest.of(page, size);
			Page<Bill> orderResponses = orderRepo.findAllByUserIdAndStatus(userId, EStatus.ACTIVE.getName(), pageable);

			return orderConverter.convertToResponse(orderResponses);


		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}


	}

	@Override
	public OrderResponse getById(Long id) {
		return orderConverter.convertToResponse(orderRepo.findById(id).get());
	}

}
