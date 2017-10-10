package com.github.philipealves.heimdall.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.github.philipealves.heimdall.auth.LoggedUser;
import com.github.philipealves.heimdall.response.UserResponse;
import com.github.philipealves.heimdall.utils.HeimdallUtils;
import com.github.philipealves.heimdall.utils.ResponseCode;

@Component
public class HeimdallAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		LoggedUser user = (LoggedUser) authentication.getPrincipal();
		UserResponse userResponse = new UserResponse(user.getUsername(), user.getName(), user.getEmail(),
				user.getPassword(), user.isEnabled());
		String retorno = HeimdallUtils.toJson(ResponseCode.SUCCESS, userResponse);
		HeimdallUtils.printResponse(response, HttpServletResponse.SC_OK, retorno);
	}

}
