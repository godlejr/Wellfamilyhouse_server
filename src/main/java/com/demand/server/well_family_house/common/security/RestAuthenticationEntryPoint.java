package com.demand.server.well_family_house.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;


public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		//request without any credentials
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "À£ÆÐ¹Ð¸® ÇÏ¿ì½º ¹Ì½ÂÀÎ  by the developer 'DongJoo KIM' of Demand corporation.");
	}

}
