package com.example.befruit.controller.admin;

import com.example.befruit.dto.UserDTO;
import com.example.befruit.dto.response.UserResponse;
import com.example.befruit.entity.ResponseObject;
import com.example.befruit.sercurity.service.UserDetail;
import com.example.befruit.service.IProductService;
import com.example.befruit.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
//@PreAuthorize("hasAuthority('admin')")
@RequestMapping("/api/admin/user")
public class UserManagerController {
	@Autowired
	private IUserService userService;
	@PostMapping("/add")
	public ResponseEntity<ResponseObject> add(@RequestBody UserDTO userDTO){
		try{
			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();
			UserDetail currentUserLogin =(UserDetail) authentication.getPrincipal();


			UserResponse userResponse = userService.add(userDTO);
			return ResponseEntity.ok().body(new ResponseObject("ok", "Update product successful!", userResponse));

		}catch (Exception e){
			return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), null));

		}

	}
}
