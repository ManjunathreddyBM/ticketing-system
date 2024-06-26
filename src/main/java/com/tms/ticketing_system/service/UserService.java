package com.tms.ticketing_system.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tms.ticketing_system.dto.LoginRequest;
import com.tms.ticketing_system.dto.LoginResponse;
import com.tms.ticketing_system.dto.ResponseEntity;
import com.tms.ticketing_system.dto.UserRegistrationDto;
import com.tms.ticketing_system.jwt.JwtService;
import com.tms.ticketing_system.model.Department;
import com.tms.ticketing_system.model.User;
import com.tms.ticketing_system.repository.DepartmentRepository;
import com.tms.ticketing_system.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	public ResponseEntity<User> createUser(UserRegistrationDto userDto) {
		Department department = departmentRepository.findByName(userDto.getDepartment());
//			    .orElseGet(() -> {
//			        Department newDepartment = new Department();
//			        newDepartment.setName(userDto.getDepartmentName());
//			        return departmentRepository.save(newDepartment);
//			    });
		User user = userRepository.findByEmail(userDto.getEmail());
		if(user != null) {
			String message =  "User with Email " + user.getEmail() + " already Exists";
			return new ResponseEntity<>(message, user);
		}
		System.out.println("CREATING NEW USER >>>>>>>>>>>>>>>>> "+ user);
		if(department == null ) {
			System.out.println("Department not exists... Creating new one...");
			Department newDepartment = new Department();
			newDepartment.setName(userDto.getDepartment());
			department = departmentRepository.save(newDepartment);
		}
		
		User newUser = new User();
		newUser.setName(userDto.getUsername());
		newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
		newUser.setEmail(userDto.getEmail());
		newUser.setDepartment(department);
		newUser.setRole("ROLE_"+userDto.getRole().toUpperCase());
		User savedUser = userRepository.save(newUser);
		String message = "New USer Created : " + savedUser.getName();
		System.out.println(message);
		return new ResponseEntity<>(message, savedUser);
	}
	
	public List<User> getUsers() {
		System.out.println("GNG TO GET USERS");
		List<User> users = userRepository.findAll();
		System.out.println("GOT USERS :" + users.size());
		return users;
	}
	
	public LoginResponse login(LoginRequest login) {
		
		System.out.println("LOGIN STARTED");
		
		Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUserName(), login.getPassword()));
		if(auth.isAuthenticated()) {
			String token = jwtService.generateToken(login.getUserName());
			String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), token);
			User user = userRepository.findByEmail(login.getUserName());
			LoginResponse reponse = new LoginResponse(token, refreshToken, user);
			return reponse;
		} else
			throw new RuntimeException("USER NOT FOUND");
	}
}
