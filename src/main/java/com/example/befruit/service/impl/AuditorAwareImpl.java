package com.example.befruit.service.impl;

import com.example.befruit.sercurity.service.UserDetail;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;


public class AuditorAwareImpl implements AuditorAware<String>  {

	@Override
	public Optional<String> getCurrentAuditor(){

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return Optional.empty();
		}
		try{
			UserDetail u =(UserDetail) authentication.getPrincipal();
			return Optional.of(u.getEmail());
		}catch (Exception e){
			return Optional.of("");
		}


	}


}