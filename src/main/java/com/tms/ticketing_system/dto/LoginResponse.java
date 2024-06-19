package com.tms.ticketing_system.dto;

import org.springframework.stereotype.Component;

import com.tms.ticketing_system.model.User;

@Component
public class LoginResponse {

	private String token;
	
	private String refreshToken;

	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LoginResponse() {
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public LoginResponse(String token, String refreshToken, User user) {
		super();
		this.token = token;
		this.refreshToken = refreshToken;
		this.user = user;
	}
	
}
