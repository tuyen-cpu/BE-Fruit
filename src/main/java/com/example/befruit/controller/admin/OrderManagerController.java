package com.example.befruit.controller.admin;

import com.example.befruit.dto.response.OrderResponse;
import com.example.befruit.dto.response.UserResponse;
import com.example.befruit.entity.ResponseObject;
import com.example.befruit.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/order")
public class OrderManagerController {
	@Autowired
	private IOrderService orderService;
@GetMapping("/all")
	public ResponseEntity<ResponseObject> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
																							 @RequestParam(name = "size", defaultValue = "10") int size){

	try {
		Page<OrderResponse> orderResponses = orderService.getAll(page,size);
		return ResponseEntity.ok()
				.body(new ResponseObject("ok", "Get orders successfully!", orderResponses));
	} catch (Exception e) {
		return ResponseEntity.badRequest()
				.body(new ResponseObject("ok", e.getMessage(), ""));
	}
}

}
