package com.example.befruit.controller.admin;

import com.example.befruit.dto.request.OrderRequest;
import com.example.befruit.dto.response.OrderResponse;
import com.example.befruit.dto.response.ProductResponse;
import com.example.befruit.dto.response.UserResponse;
import com.example.befruit.entity.*;
import com.example.befruit.repo.specs.EntitySpecification;
import com.example.befruit.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	@GetMapping("/filter")
	public ResponseEntity<ResponseObject> filter(@RequestParam() MultiValueMap<String, String> request
	) {
		try {
			int[] paginator ={0,0};
			EntitySpecification<Bill> orderSpecifications = new EntitySpecification<>();
			request.forEach((k, value) -> {
				if(convertWithoutUnderStoke(k).equals("page")){
					paginator[0]=Integer.parseInt(value.get(0));
				}else if(convertWithoutUnderStoke(k).equals("size")){
					paginator[1]=Integer.parseInt(value.get(0));
				}
				else if (!convertWithoutUnderStoke(k).equals("address")&&isNumber(value.get(0))) {
					orderSpecifications.add(new Filter(k, QueryOperator.EQUAL ,value.get(0)));
				} else{
					orderSpecifications.add(new Filter(k, QueryOperator.IN ,value.get(0)));
				}
			});
			Page<OrderResponse>  orderResponsePage = orderService.filter(orderSpecifications,paginator[0],paginator[1]);


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
