package com.tms.ticketing_system.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import com.tms.ticketing_system.dto.LoginRequest;
import com.tms.ticketing_system.dto.LoginResponse;
import com.tms.ticketing_system.dto.ResponseEntity;
import com.tms.ticketing_system.dto.UserRegistrationDto;
import com.tms.ticketing_system.jwt.UserServiceJwt;
import com.tms.ticketing_system.model.User;
import com.tms.ticketing_system.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserServiceJwt userServiceJwt;
	
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
	@PreAuthorize("hasRole('ADMIN')")
	public List<User> getUsers() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    System.out.println("Authenticated User: " + authentication.getName());
	    System.out.println("Roles: " + authentication.getAuthorities());
	    
//		System.out.println(userService.createUser(new User()));
		List<User> users = userService.getUsers();
		System.out.println("BACK FROM FINDING USERS");
		return users;
	}
	
	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest loginRequest) {
		LoginResponse response =  userService.login(loginRequest);
		UserDetails u = userServiceJwt.loadUserByUsername(loginRequest.getUserName());
	    System.out.println("Roles: " + u.getAuthorities());
		return response;
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
	
	@ExceptionHandler(Forbidden.class)
    public org.springframework.http.ResponseEntity<ResponseEntity<String>> forbidden(Forbidden ex) {
//        String errors = ex.getCause()
//                                .getMessage();
                                
        return org.springframework.http.ResponseEntity.status(HttpStatus.FORBIDDEN)
                             .body(new ResponseEntity<>("ACCESS DENIED. YOU DONT HAVE ACCESS TO USE THIS", "NO ACCESS"));
	}
	
	@ExceptionHandler(AccessDeniedException.class)
    public org.springframework.http.ResponseEntity<ResponseEntity<String>> handleAccessDeniedException(AccessDeniedException ex) {
        
		return org.springframework.http.ResponseEntity.status(HttpStatus.FORBIDDEN)
                             .body(new ResponseEntity<>("ACCESS DENIED. YOU DONT HAVE ACCESS TO USE THIS", ex.getMessage()));
    }
	public AccessDeniedHandler accessDeniedHandler() {
	    return (request, response, accessDeniedException) -> {
	        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	        response.getWriter().write("ACCESS DENIED. YOU DON'T HAVE ACCESS TO USE THIS.");
	    };
	}
	
	@ExceptionHandler(Unauthorized.class)
    public org.springframework.http.ResponseEntity<ResponseEntity<String>> unAuthorized(Unauthorized ex) {
        String errors = ex.getCause()
                                .getMessage();
                                
        return org.springframework.http.ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(new ResponseEntity<>("ACCESS DENIED. YOU DONT HAVE ACCESS TO USE THIS", errors));
	}

}
