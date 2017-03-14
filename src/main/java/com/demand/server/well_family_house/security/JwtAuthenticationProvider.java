package com.demand.server.well_family_house.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.demand.server.well_family_house.util.JwtUtil;

public class JwtAuthenticationProvider implements AuthenticationProvider {
	private JwtUtil jwtUtil;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String token = (String) authentication.getCredentials();
		jwtUtil = new JwtUtil();
		int user_level = jwtUtil.getUser_level(token);

		System.out.println("test2");

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

		if (user_level == 9) {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			System.out.println("ROLE_ADMIN");
		} else {
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			System.out.println("ROLE_USER");
		}
		System.out.println("test3");
		authentication = new UsernamePasswordAuthenticationToken("demand", "demand8312", grantedAuthorities);
		// SecurityContextHolder.setContext();
		return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
