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
import com.example.befruit.repo.UserRepo;
import com.example.befruit.repo.specs.EntitySpecification;
import com.example.befruit.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
	private UserRepo userRepo;
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

			ShippingStatus shippingStatus = shippingStatusService.getByName(EShippingStatus.UNVERIFIED.getName());
			shippingStatus.setName(EShippingStatus.UNVERIFIED.getName());
			User user = userRepo.findById(orderRequest.getUserId())
					.orElseThrow(() -> new EntityNotFoundException("User "+orderRequest.getUserId()+" does not exist!"));
			Payment p = orderRequest.getPayment();
			Bill order = new Bill();

			order.setStatus(EStatus.ACTIVE.getName());
			order.setShippingStatus(shippingStatus);
			order.setUser(user);
			order.setAddress(orderRequest.getAddress());
			order.setDescription(orderRequest.getDescription());
			long total = 0L;
			List<OrderDetail> orderDetails = orderRequest.getOrderDetails().stream().map(dto -> orderDetailConverter.convertToEntity(dto)).collect(Collectors.toList());
			orderDetails.forEach(e -> e.setBill(order));

			for (OrderDetail orderDetail : orderDetails) {
				total += (orderDetail.getPrice() - orderDetail.getPrice() * orderDetail.getDiscount() / 100) * orderDetail.getQuantity();
			}
			order.setTotal(total);
			order.setOrderDetails(orderDetails);
			p.setBill(order);
			order.setPayment(p);
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
		return orderConverter.convertToResponse(orderRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User "+id+" does not exist!")));
	}

	@Override
	public OrderResponse updateStatusShipping(Long id, String status) {
		ShippingStatus shippingStatus = shippingStatusService.getByName(status);
		Bill order = orderRepo.findById(id)
					.orElseThrow(() -> new EntityNotFoundException("User "+id+" does not exist!"));
		order.setShippingStatus(shippingStatus);
		return orderConverter.convertToResponse(orderRepo.save(order));
	}

	@Override
	public Page<OrderResponse> getAll(Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
		return orderConverter.convertToResponse(orderRepo.findAll(pageable));
	}

	@Override
	public Page<OrderResponse> filter(EntitySpecification<Bill> productSpecification, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
		return orderConverter.convertToResponse(orderRepo.findAll(productSpecification,pageable));
	}

	@Override
	public OrderResponse updateShippingStatus(Long id, ShippingStatus shippingStatus) {
		Bill bill = orderRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Order "+id+" does not exist!"));
		bill.setShippingStatus(shippingStatus);
		return orderConverter.convertToResponse(orderRepo.save(bill));
	}

}
