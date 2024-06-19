package com.tms.ticketing_system.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.tms.ticketing_system.model.User;
import com.tms.ticketing_system.repository.UserRepository;

@Component
public class UserServiceJwt implements UserDetailsService{

	@Autowired
	public UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername " );
		User user = userRepository.findByEmail(username);	
		if(user == null) {
			throw new RuntimeException("USER NOT FOUND");
		}
		return user;
	}

}
