package com.portmanagement.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.portmanagement.entity.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Service
public class JWTService {
	
	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private long expiration;
	
	
	private SecretKey getKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}
	
	public String generateToken(User user) {
		return Jwts.builder()
				.subject(user.getEmail())
				.claim("ROLE", user.getRole())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+expiration))
				.signWith(getKey())
				.compact();
	}
	
	public String extractUserName(String token) {
		return Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
	
	public String extractUserRole(String token) {
		return Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.get("ROLE",String.class);
	}
	
	public boolean isTokenValid(String token) {
		try {
			Jwts.parser()
			.verifyWith(getKey())
			.build()
			.parseSignedClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
