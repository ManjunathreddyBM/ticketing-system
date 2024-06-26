package com.tms.ticketing_system.security;

import java.io.IOException;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLoggingFilter extends OncePerRequestFilter {
	
	@Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) 
            throws ServletException, IOException {
        
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Security Context: " + SecurityContextHolder.getContext().getAuthentication());
        
        filterChain.doFilter(request, response);
        
        System.out.println("Response Status: " + response.getStatus());
    }



	
}
