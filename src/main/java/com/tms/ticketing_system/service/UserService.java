package com.tms.ticketing_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tms.ticketing_system.model.User;
import com.tms.ticketing_system.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public String createUser(User user) {
		return "User Created";
	}
	
	public List<User> getUsers() {
		System.out.println("GNG TO GET USERS");
		List<User> users = userRepository.findAll();
		System.out.println("GOT USERS :" + users.size());
		return users;
	}
}
