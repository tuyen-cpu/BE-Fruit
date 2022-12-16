package com.example.befruit.service;

import com.example.befruit.entity.Role;

import java.util.List;

public interface IRoleService {
	Role getRoleByName(String name);
	List<Role> getRoleByUserId(Long userId) ;
}
