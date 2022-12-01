package com.example.befruit.sercurity.jwt.payload.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class JwtResponse implements Serializable {
	private String token;
	private String type = "Bearer";
	private String refreshToken;
	private Long id;
	private String username;
	private String email;
	private String firstName;
	private String lastName;
	private List<String> roles;

	public JwtResponse(String accessToken, String refreshToken, Long id, String username, String email,String firstName,String lastName, List<String> roles) {
		this.token = accessToken;
		this.refreshToken = refreshToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.firstName=firstName;
		this.lastName=lastName;
	}
}
