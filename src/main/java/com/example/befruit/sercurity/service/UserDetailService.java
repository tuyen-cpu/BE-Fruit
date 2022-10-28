package com.example.befruit.sercurity.service;

import com.example.befruit.entity.User;
import com.example.befruit.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

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
        User user = userRepo.findByEmailAndStatus(email,1);
        if(user==null) {
            throw new UsernameNotFoundException("Email "+email+" chưa được đăng ký!");
        }
        return new UserDetail(user);
    }
}
