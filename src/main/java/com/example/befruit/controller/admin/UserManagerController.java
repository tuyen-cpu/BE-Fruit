package com.example.befruit.controller.admin;

import com.example.befruit.dto.UserDTO;
import com.example.befruit.dto.response.UserResponse;
import com.example.befruit.entity.*;
import com.example.befruit.repo.specs.EntitySpecification;
import com.example.befruit.sercurity.service.UserDetail;
import com.example.befruit.service.IUserService;
import com.example.befruit.service.impl.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
//@PreAuthorize("hasAuthority('admin')")
@RequestMapping("/api/admin/user")
public class UserManagerController {
	@Autowired
	private IUserService userService;
	@Autowired
	private RoleService roleService;
	@GetMapping("/all")
	public ResponseEntity<ResponseObject> user( @RequestParam(name = "page", defaultValue = "0") int page,
																							@RequestParam(name = "size", defaultValue = "10") int size) {
		try {
			Page<UserResponse> user = userService.getAll(page,size);
			return ResponseEntity.ok()
					.body(new ResponseObject("ok", "Get user successfully!", user));
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new ResponseObject("ok", "Get failed!", ""));
		}

	}
	@PostMapping("/add")
	public ResponseEntity<ResponseObject> add(@RequestBody UserDTO userDTO){
		try{
//			Authentication authentication = SecurityContextHolder.getContext()
//					.getAuthentication();
//			UserDetail currentUserLogin =(UserDetail) authentication.getPrincipal();

			UserResponse userResponse = userService.add(userDTO);
			return ResponseEntity.ok().body(new ResponseObject("ok", "Added product successful!", userResponse));

		}catch (Exception e){
			return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), null));

		}

	}
	@PutMapping("/edit")
	public ResponseEntity<ResponseObject> edit(@RequestBody UserDTO userDTO){
		try{
			System.out.println(userService.countByRoleName(ERole.ADMIN.getName())+"tuyen");
			Authentication authentication = SecurityContextHolder.getContext()
					.getAuthentication();
			UserDetail currentUserLogin =(UserDetail) authentication.getPrincipal();
			User userIsEdited= userService.getUserByEmail(userDTO.getEmail());

			if(currentUserLogin.getAuthorities().stream().anyMatch(e -> e.toString().equals("admin"))
					&&userIsEdited.getEmail().equals(currentUserLogin.getEmail())
					&&!userDTO.getRoles()[0].equals("admin")
					&&userService.countByRoleName(ERole.ADMIN.getName())<=1){
				throw new RuntimeException("Can't delete the only admin account!");
			}

			UserResponse userResponse = userService.edit(userDTO);
			return ResponseEntity.ok().body(new ResponseObject("ok", "Update product successful!", userResponse));

		}catch (Exception e){
			return ResponseEntity.badRequest().body(new ResponseObject("failed", e.getMessage(), null));
		}
	}
	@GetMapping("/filter")
	public ResponseEntity<ResponseObject> user(@RequestParam() MultiValueMap<String, String> request
	) {
		try {
			int[] paginator ={0,0};
			EntitySpecification<User> userSpecifications = new EntitySpecification<User>();
			request.forEach((k, value) -> {
				if(convertWithoutUnderStoke(k).equals("page")){
					paginator[0]=Integer.parseInt(value.get(0));
				}else if(convertWithoutUnderStoke(k).equals("size")){
					paginator[1]=Integer.parseInt(value.get(0));
				} else if (isNumber(value.get(0))) {
					userSpecifications.add(new Filter(k, QueryOperator.EQUAL ,value.get(0)));
				} else{
					userSpecifications.add(new Filter(k, QueryOperator.IN ,value.get(0)));
				}
			});
			Page<UserResponse>  userResponses = userService.filter(userSpecifications,paginator[0],paginator[1]);
			System.out.println(userResponses.toString());

			return ResponseEntity.ok()
					.body(new ResponseObject("ok", "Get user successfully!", userResponses));
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
	private List<String> splitCommon(List<String> list){
		return	list.stream()
				.flatMap(Pattern.compile(",")::splitAsStream)
				.collect(Collectors.toList());

	}
}
