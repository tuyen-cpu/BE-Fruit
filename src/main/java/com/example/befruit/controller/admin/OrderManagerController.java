package com.example.befruit.controller.admin;

import com.example.befruit.dto.filter.OrderFilter;
import com.example.befruit.dto.response.OrderResponse;
import com.example.befruit.entity.*;
import com.example.befruit.repo.specs.OrderSpecification;
import com.example.befruit.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/order")
public class OrderManagerController {
	@Autowired
	private IOrderService orderService;

	@PostMapping("/update-status")
	public ResponseEntity<ResponseObject> getAll(@RequestBody OrderResponse orderResponse){

		try {
		OrderResponse orderResult = orderService.updateShippingStatus(orderResponse.getId(),orderResponse.getShippingStatus());
			return ResponseEntity.ok()
					.body(new ResponseObject("ok", "Update orders successfully!", orderResult));
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new ResponseObject("ok", e.getMessage(), ""));
		}
	}

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
	@PostMapping("/filter")
	public ResponseEntity<ResponseObject> filter(@RequestBody OrderFilter orderFilter
																							 ) {
		try {
			OrderSpecification orderSpecification = new OrderSpecification();
			if(orderFilter.getCreatedDate()==null||orderFilter.getCreatedDate().size()<1){
			}
			else if(orderFilter.getCreatedDate().get(0)==null){
				orderSpecification.add(new Filter("createdDate", QueryOperator.IN ,orderFilter.getCreatedDate().get(1)));
			}else if(orderFilter.getCreatedDate().get(1)==null){
				orderSpecification.add(new Filter("createdDate", QueryOperator.IN ,orderFilter.getCreatedDate().get(0)));
			}else if(orderFilter.getCreatedDate().get(0)!=null && orderFilter.getCreatedDate().get(1)!=null){
				orderSpecification.add(new Filter("createdDate", QueryOperator.IN ,orderFilter.getCreatedDate()));
			}
			if(orderFilter.getAddress()!=null){
				orderSpecification.add(new Filter("address", QueryOperator.LIKE ,orderFilter.getAddress()));
			}
			if(orderFilter.getPayment()!=null){
				orderSpecification.add(new Filter("payment", QueryOperator.EQUAL ,orderFilter.getPayment()));
			}
			if(orderFilter.getShippingStatusId()!=null){
				orderSpecification.add(new Filter("shippingStatusId", QueryOperator.EQUAL ,orderFilter.getShippingStatusId()));
			}

			Page<OrderResponse>  orderResponsePage = orderService.filter(orderSpecification,orderFilter.getPage(),orderFilter.getSize());


			return ResponseEntity.ok()
					.body(new ResponseObject("ok", "Filter product successfully!", orderResponsePage));
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new ResponseObject("failed", e.getMessage(), ""));
		}
	}
	public static boolean isNumber(String text){
		return text.chars().allMatch(Character::isDigit);
	}
	private String convertWithoutUnderStoke(String str){
		return str.split("_")[0];
	}
}
