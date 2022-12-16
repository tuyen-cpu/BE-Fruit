package com.example.befruit.service.impl;

import com.example.befruit.entity.Role;
import com.example.befruit.repo.RoleRepo;
import com.example.befruit.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService {
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public Role getRoleByName(String name) {
		return null;
	}

	@Override
	public List<Role> getRoleByUserId(Long userId) {
		List<Role> roles= roleRepo.findAllByUserId(userId);
		return roles;
	}


}
