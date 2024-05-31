package com.tms.ticketing_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tms.ticketing_system.model.User;
import com.tms.ticketing_system.service.UserService;


@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public String test() {
		System.out.println("TESTING");
		return "TESTING SUCCESS";
	}
	
	
	@GetMapping("/getusers")
	public List<User> getUsers() {
		System.out.println(userService.createUser(new User()));
		List<User> users = userService.getUsers();
		System.out.println("BACK FROM FINDING USERS");
		return users;
	}
}
