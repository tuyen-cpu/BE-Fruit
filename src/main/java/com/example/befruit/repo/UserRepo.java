package com.example.befruit.repo;

import com.example.befruit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

//@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	User findByVerificationCode(String code);

	User findByEmail(String email);

	User findByUserNameAndStatus(String userName, Integer status);

	User findByEmailAndStatus(String userName, Integer status);

	boolean existsByUserName(String username);

	boolean existsByEmail(String email);

	User findByToken(String token);
}
