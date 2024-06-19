package com.tms.ticketing_system.jwt;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	public String generateToken(String username) {
		System.out.println("generateToken");
		return Jwts.builder()
		.subject(username)
		.issuedAt(new Date(System.currentTimeMillis()))
		.expiration(new Date(System.currentTimeMillis() +  1 * 24 * 60 * 60 * 1000L))
		.signWith(getSignKey())
		.compact();
	}
	
	public String generateRefreshToken(Map<String, Object> extraClaims, String userName) {
		return Jwts.builder()
				.claims(extraClaims)
				.subject(userName)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() +  2 * 24 * 60 * 60 * 1000L))
				.signWith(getSignKey()).compact();
	}
	
	public Key getSignKey() {
		System.out.println("getSignKey");
		byte[] key = Decoders.BASE64.decode("aDJYwlONyZ53iwdM0SLGcZwsuXfGZ29o524WcNzGan8=");
		return Keys.hmacShaKeyFor(key);
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	public String extractUserName(String token) {
		System.out.println("extractUserName ");
		return extractClaim(token, Claims::getSubject);
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUserName(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}
}
