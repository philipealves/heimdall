package com.github.philipealves.heimdall.filter;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.philipealves.heimdall.auth.handler.HeimdallAuthenticationFailureHandler;
import com.github.philipealves.heimdall.auth.handler.HeimdallAuthenticationSuccessHandler;
import com.github.philipealves.heimdall.auth.service.TokenAuthenticationService;
import com.github.philipealves.heimdall.model.User;

public class JwtAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

	private final HeimdallAuthenticationSuccessHandler successHandler;
	private final HeimdallAuthenticationFailureHandler failureHandler;

	public JwtAuthenticationProcessingFilter(String url, AuthenticationManager authManager,
			HeimdallAuthenticationSuccessHandler successHandler, HeimdallAuthenticationFailureHandler failureHandler) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
		this.successHandler = successHandler;
		this.failureHandler = failureHandler;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		User credentials = new ObjectMapper().readValue(request.getInputStream(), User.class);

		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
				credentials.getUsername(), credentials.getPassword(), Collections.emptyList()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain, Authentication auth) throws IOException, ServletException {
		TokenAuthenticationService.addAuthentication(response, auth.getName());
		this.successHandler.onAuthenticationSuccess(request, response, auth);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		this.failureHandler.onAuthenticationFailure(request, response, failed);
	}

}