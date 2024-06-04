package com.tms.ticketing_system.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
        		//.requestMatchers("/users/getusers").denyAll()
        		.requestMatchers(request -> "/users/login".equals(request.getServletPath())).denyAll() // Deny access to /users/getusers
        		.anyRequest().permitAll()
            //.requestMatchers("/users/getusers", "/users", "/users/register", "/users/test").permitAll()
           // .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .permitAll()
        )
        .logout(logout -> logout
            .permitAll()
        );

    return http.build();
		
	}
	

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
