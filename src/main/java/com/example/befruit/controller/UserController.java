package com.example.befruit.controller;

import com.example.befruit.dto.UserDTO;
import com.example.befruit.dto.request.ChangePasswordRequest;
import com.example.befruit.entity.ResponseObject;
import com.example.befruit.entity.User;
import com.example.befruit.sercurity.service.UserDetail;
import com.example.befruit.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class UserController {
	@Autowired
	private IUserService userService;
	@Autowired
	AuthenticationManager authenticationManager;
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
	@PostMapping("/change-password")
	public ResponseEntity<ResponseObject> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
		try {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(changePasswordRequest.getEmail(), changePasswordRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetail userDetail = (UserDetail) authentication.getPrincipal();
		if(userDetail==null){
			return ResponseEntity.badRequest()
					.body(new ResponseObject("failed", "Invalid password!", ""));
		}
			userService.changePassword(changePasswordRequest.getEmail(),changePasswordRequest.getNewPassword());
			return ResponseEntity.ok()
					.body(new ResponseObject("ok", "Changed password successfully!", ""));
		} catch (BadCredentialsException e) {
			return ResponseEntity.badRequest()
					.body(new ResponseObject("failed", "Invalid password!", ""));
		}


	}
}
