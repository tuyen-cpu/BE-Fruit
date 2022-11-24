package com.example.befruit.service.impl;

import com.example.befruit.sercurity.service.UserDetail;
import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
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
			return null;
		}

		return Optional.of(((UserDetail) authentication.getPrincipal()).getUsername());
	}


}