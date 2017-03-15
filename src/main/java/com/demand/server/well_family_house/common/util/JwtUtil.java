package com.demand.server.well_family_house.common.util;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	private final String secret_key = "demand8312";

	public int getUser_level(String token) {
		Claims body = Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token).getBody();
		return Integer.valueOf((String) body.get("user_level"));
	}

	public String generateToken(int user_level) {
		Claims claims = Jwts.claims().setSubject("demand");
		claims.put("user_level", user_level + "");
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret_key).compact();
	}

}