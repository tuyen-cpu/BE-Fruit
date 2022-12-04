package com.example.befruit.controller;

import com.example.befruit.dto.AddressDTO;
import com.example.befruit.entity.EStatus;
import com.example.befruit.entity.ResponseObject;
import com.example.befruit.service.impl.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
public class AddressController {
	@Autowired
	private AddressService addressService;

	@GetMapping("/{userId}")
	public ResponseEntity<ResponseObject> getAllByUserId(@PathVariable(name = "userId", required = false) long userId,
																											 @RequestParam(name = "page", defaultValue = "0") int page,
																											 @RequestParam(name = "size", defaultValue = "10") int size) {
		try {
			System.out.println(userId);
			Page<AddressDTO> addressDTOS = addressService.getAllByUserId(userId, EStatus.ACTIVE.getName(), page, size);
			return ResponseEntity.ok().body(new ResponseObject("ok", "Get address for user id: " + userId + " successful!", addressDTOS));

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));

		}


	}

	@PostMapping("/add")
	public ResponseEntity<ResponseObject> add(@RequestBody AddressDTO addressDTO) {
		try {
			addressDTO.setStatus(1);
			if (addressDTO.getIsDefault() == null || addressDTO.getIsDefault().equals("")) {
				addressDTO.setIsDefault(0);
			}
			AddressDTO address = addressService.add(addressDTO);
			return ResponseEntity.ok().body(new ResponseObject("ok", "add address successful!", address));

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));

		}


	}

	@PutMapping("/update")
	public ResponseEntity<ResponseObject> update(@RequestBody AddressDTO addressDTO) {
		try {
			addressDTO.setStatus(1);
			AddressDTO address = addressService.update(addressDTO);
			return ResponseEntity.ok().body(new ResponseObject("ok", "update address successful!", address));

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));
		}
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseObject> delete(@PathVariable("id") Long id) {
		try {
			addressService.delete(id);
			return ResponseEntity.ok().body(new ResponseObject("ok", "Deleted address!", ""));

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), ""));

		}


	}
}
