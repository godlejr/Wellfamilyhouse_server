package com.demand.server.well_family_house.common.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.demand.server.well_family_house.common.util.JwtUtil;

import io.jsonwebtoken.MalformedJwtException;

public class JwtAuthenticationProvider implements AuthenticationProvider {
	private JwtUtil jwtUtil;
	private int user_level;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String token = (String) authentication.getCredentials();
		jwtUtil = new JwtUtil();

		try {
			user_level = jwtUtil.getUser_level(token);
		} catch (MalformedJwtException e) {
			throw new AuthenticationServiceException(
					"웰패밀리하우스  by the developer 'DongJoo KIM' of Demand corporation.");
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

		if (user_level == 9) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} else {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		authentication = new UsernamePasswordAuthenticationToken("demand", "demand8312", grantedAuthorities);

		return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
