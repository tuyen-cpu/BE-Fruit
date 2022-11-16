package com.example.befruit.service.impl;

import com.example.befruit.entity.ShippingStatus;
import com.example.befruit.repo.ShippingStatusRepo;
import com.example.befruit.service.IShippingStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippingStatusService implements IShippingStatusService {
	@Autowired
	private ShippingStatusRepo shippingStatusRepo;

	@Override
	public ShippingStatus getByName(String name) {
		return shippingStatusRepo.findByName(name);
	}
}
