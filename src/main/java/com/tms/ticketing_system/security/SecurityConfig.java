package com.tms.ticketing_system.security;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tms.ticketing_system.jwt.JwtAuthenticationFilter;
import com.tms.ticketing_system.jwt.UserServiceJwt;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{
	
	@Autowired
	public UserServiceJwt userServiceJwt;
	
	@Autowired
	public JwtAuthenticationFilter jwtAuthenticationFilter;
	
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//        .csrf(csrf -> csrf.disable())
//        .authorizeHttpRequests(auth -> auth
//        		//.requestMatchers("/users/getusers").denyAll()
//        		//.requestMatchers(request -> "/users/login".equals(request.getServletPath())).denyAll() // Deny access to /users/getusers
//        		.anyRequest().permitAll()
//            //.requestMatchers("/users/getusers", "/users", "/users/register", "/users/test").permitAll()
//           // .anyRequest().authenticated()
//        )
//        .formLogin(form -> form
//            .permitAll()
//        )
//        .logout(logout -> logout
//            .permitAll()
//        );
//
//    return http.build();
//		
//	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());
		
		http.authorizeHttpRequests(requests -> requests
				.requestMatchers("/users","/users/login","/users/register").permitAll()
				.requestMatchers("/users/getusers").hasRole("USER")
				.anyRequest().authenticated());
//				.anyRequest().permitAll());
		
		
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		http.exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler()));
		return http.build();
	}
	
	public AccessDeniedHandler accessDeniedHandler() {
		return new AccessDeniedHandlerImpl();
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
		System.out.println(">>>>>>>>>>>>>>>> authenticationProvider <<<<<<<<<<<<<<<<<<");
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userServiceJwt);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration congfig) throws Exception {
    	return congfig.getAuthenticationManager();
    }
    
}
