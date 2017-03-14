package com.demand.server.well_family_house.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter{
	public final String HEADER ="Authentication";
	private UsernamePasswordAuthenticationToken authRequest;

	@Autowired
	private AuthenticationManager authenticationManager;

	protected JwtAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationSuccessHandler(new UrlAuthenticationSuccessHandler());
        setAuthenticationManager(authenticationManager);
	}



	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
			String token = request.getHeader(HEADER);

	        if (token == null) {
	            throw new AuthenticationServiceException("À£ÆÐ¹Ð¸® ÇÏ¿ì½º ¹Ì½ÂÀÎ  by the developer 'DongJoo KIM' of Demand corporation.");
	        }else{
	        	authRequest = new UsernamePasswordAuthenticationToken("demand",token);
	        }
			System.out.println("test1");

	        return getAuthenticationManager().authenticate(authRequest);
	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		super.doFilter(request, response, chain);
	}

}
