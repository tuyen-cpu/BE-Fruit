package com.example.befruit.service;

import com.example.befruit.dto.UserDTO;
import com.example.befruit.entity.User;

import java.util.List;

public interface IUserService {
    UserDTO add(User user);
     List<UserDTO> getUsers();
     User getUserByToken(String token);
     UserDTO getUserById(long id);
    String getTokenByUserId(Long id);
    void register(UserDTO userDTO,String siteURL);
    Boolean verify(String verificationCode);
}
