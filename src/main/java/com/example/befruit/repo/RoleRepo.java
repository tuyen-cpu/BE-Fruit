package com.example.befruit.repo;

import com.example.befruit.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {
	Role findByName(String name);

}
