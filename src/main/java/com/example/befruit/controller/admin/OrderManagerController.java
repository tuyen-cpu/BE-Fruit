package com.example.befruit.controller.admin;

import com.example.befruit.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/order")
public class OrderManagerController {
	@Autowired
	private IOrderService orderService;


}
