package com.example.befruit.controller;

import com.example.befruit.dto.UserDTO;
import com.example.befruit.entity.ResponseObject;
import com.example.befruit.entity.User;
import com.example.befruit.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class UserController {
	@Autowired
	private IUserService userService;

	@GetMapping("/all")
	public ResponseEntity<ResponseObject> user() {
		try {
			UserDTO user = userService.getUserById(52);
			return ResponseEntity.ok()
					.body(new ResponseObject("ok", "Success user!", user));
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new ResponseObject("ok", "get failed", ""));
		}


	}
	@PutMapping("/update")
	public ResponseEntity<ResponseObject> update(@RequestBody UserDTO userDTO) {
		System.out.println(userDTO.getFirstName());
		try {
			UserDTO user = userService.update(userDTO);
			return ResponseEntity.ok()
					.body(new ResponseObject("ok", "Update successful!", user));
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new ResponseObject("ok", e.getMessage(), ""));
		}


	}

}
