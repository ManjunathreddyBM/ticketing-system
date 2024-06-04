package com.tms.ticketing_system.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tms.ticketing_system.dto.ResponseEntity;
import com.tms.ticketing_system.dto.UserRegistrationDto;
import com.tms.ticketing_system.model.User;
import com.tms.ticketing_system.service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("users")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public String test() {
		System.out.println("TESTING");
		return "TESTING SUCCESS";
	}
	
//	@PostMapping("/register")
    @PostMapping(value = "/register", consumes = {"application/json","multipart/form-data"}, produces = "application/json")
	public ResponseEntity<User> createUser(@Valid @RequestBody UserRegistrationDto userDto) {
		return userService.createUser(userDto);
	}
	
	
	@GetMapping("/getusers")
	public List<User> getUsers() {
//		System.out.println(userService.createUser(new User()));
		List<User> users = userService.getUsers();
		System.out.println("BACK FROM FINDING USERS");
		return users;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public org.springframework.http.ResponseEntity<ResponseEntity<List<String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                                .getAllErrors()
                                .stream()
                                .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
                                .collect(Collectors.toList());
        return org.springframework.http.ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(new ResponseEntity<>("Validation failed", errors));
	}
}
