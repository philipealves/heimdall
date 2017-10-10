package com.github.philipealves.heimdall.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.github.philipealves.heimdall.utils.HeimdallUtils;
import com.github.philipealves.heimdall.utils.ResponseCode;

@Component
public class HeimdallAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String retorno = HeimdallUtils.toJson(ResponseCode.ACCESS_DENIED.getCode(), exception.getMessage());
		HeimdallUtils.printResponse(response, HttpServletResponse.SC_UNAUTHORIZED, retorno);
	}

}
