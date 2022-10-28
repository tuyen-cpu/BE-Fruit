package com.example.befruit.service.impl;

import com.example.befruit.entity.Role;
import com.example.befruit.repo.RoleRepo;
import com.example.befruit.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private RoleRepo roleRepo;
    @Override
    public Role getRoleByName(String name) {
        return roleRepo.findByName(name);
    }
}
