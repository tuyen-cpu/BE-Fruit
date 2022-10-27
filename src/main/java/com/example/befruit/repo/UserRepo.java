package com.example.befruit.repo;

import com.example.befruit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
     User findByVerificationCode(String code);
}
