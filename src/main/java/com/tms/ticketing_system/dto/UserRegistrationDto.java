package com.tms.ticketing_system.dto;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@Component
public class UserRegistrationDto {

	@NotBlank(message = "Username is mandatory")
	private String username;
	
	@NotBlank(message = "Password id mandatory")
	@Size(min = 6, message = "Password should be atleast 6 charecters")
	private String password;
	
	@Email(message = "Email should be valid")
	@NotBlank(message ="Email is mandatory")
	private String email;
	
	@NotBlank(message="Role is mandatory")
	private String role;
	
	@NotBlank(message="Department is mandatory")
	private String department;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
}
