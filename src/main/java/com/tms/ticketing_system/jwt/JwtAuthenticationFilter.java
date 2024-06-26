package com.tms.ticketing_system.jwt;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	public JwtService jwtService;

	@Autowired
	public UserServiceJwt userServiceJwt;

	private static final List<String> SWAGGER_WHITELIST = Arrays.asList("/v3/api-docs", "/swagger-ui",
			"/swagger-ui.html", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
			"/configuration/security", "/webjars/**");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println(">>>>>>>>>>>>>>>> JwtAuthenticationFilter <<<<<<<<<<<<<<<<<<");
		String requestURI = request.getRequestURI();

		if (SWAGGER_WHITELIST.stream().anyMatch(requestURI::startsWith)) {
			filterChain.doFilter(request, response);
			return;
		}

		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userEmail;
		System.out.println("authHeader " + authHeader);

		if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		jwt = authHeader.substring(7);
		userEmail = jwtService.extractUserName(jwt);
		System.out.println("userEmail " + userEmail);

		if (!userEmail.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userServiceJwt.loadUserByUsername(userEmail);
			System.out.println("userDetails" + userDetails.getUsername() + " ROLES " + userDetails.getAuthorities());
			if (jwtService.isTokenValid(jwt, userDetails)) {
				SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());
				token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				securityContext.setAuthentication(token);
				SecurityContextHolder.setContext(securityContext);
				System.out.println("securityContext set");
			}
		}
		filterChain.doFilter(request, response);

	}

}
