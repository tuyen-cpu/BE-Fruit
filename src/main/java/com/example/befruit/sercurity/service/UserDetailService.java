package com.example.befruit.sercurity.service;

import com.example.befruit.entity.User;
import com.example.befruit.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
	@Autowired
	UserRepo userRepo;

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
		if(user.getStatus()==0){
			throw new LockedException("Email has been locked");
		}
		return UserDetail.build(user);
	}
}
