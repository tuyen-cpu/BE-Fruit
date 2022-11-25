package com.example.befruit.service;

import com.example.befruit.dto.UserDTO;
import com.example.befruit.dto.response.UserResponse;
import com.example.befruit.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IUserService {
	UserDTO add(User user);

	List<UserDTO> getUsers();

	User getUserByToken(String token);

	UserDTO getUserById(long id);

	String getTokenByUserId(Long id);

	void register(UserDTO userDTO, String siteURL, boolean isSendMail);

	void forgotPassword(String email, String siteURL);

	Boolean verify(String verificationCode);

	void resetPassword(String verificationCode, String password);
	void changePassword(String email, String password);

	boolean checkExistByEmail(String email);

	User getUserByEmail(String email);
	UserDTO update(UserDTO userDTO);

	Page<UserResponse> getAll(int page, int size);
}
