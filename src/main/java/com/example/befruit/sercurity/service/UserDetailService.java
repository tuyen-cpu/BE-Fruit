package com.example.befruit.sercurity.service;

import com.example.befruit.entity.EStatus;
import com.example.befruit.entity.ResponseObject;
import com.example.befruit.entity.User;
import com.example.befruit.repo.UserRepo;
import com.example.befruit.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailService implements UserDetailsService {
	@Autowired
	UserRepo userRepo;
	@Lazy
	@Autowired
	UserService userService;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(email);
		if (user == null
		) {
			throw new UsernameNotFoundException("Email " + email + " is not found");
		}
		if(!user.getEnabled()){
			throw new DisabledException("Email is not verify");
		}
		if(Objects.equals(user.getStatus(), EStatus.INACTIVE.getName())){
			if (userService.unlockWhenTimeExpired(user)) {
				System.out.println("okokok");
				throw new LockedException("Your account has been unlocked. Please try to login again.");
			}
			throw new LockedException("Email has been locked");
		}
		return UserDetail.build(user);
	}
}
