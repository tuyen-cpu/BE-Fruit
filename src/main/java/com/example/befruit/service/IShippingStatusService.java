package com.example.befruit.service;

import com.example.befruit.entity.ShippingStatus;

import java.util.List;

public interface IShippingStatusService {
	ShippingStatus getByName(String name);

	List<ShippingStatus> getAll();
}
