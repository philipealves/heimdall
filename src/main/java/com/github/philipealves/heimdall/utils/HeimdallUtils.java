package com.github.philipealves.heimdall.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;

import com.github.philipealves.heimdall.response.UserResponse;
import com.google.gson.Gson;

public class HeimdallUtils {

	private static final String ISO_8859_1 = "ISO-8859-1";

	/**
	 * Return the login proccess result.
	 * 
	 * @param response
	 * @param status
	 * @param text
	 * @throws IOException
	 */
	public static void printResponse(HttpServletResponse response, int status, String text) throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(status);
		response.setCharacterEncoding(ISO_8859_1);
		response.getWriter().print(text);
		response.getWriter().flush();
	}

	/**
	 * Return a JSON object based on parameters.
	 * 
	 * @param code
	 * @param message
	 * @return String
	 */
	public static String toJson(Integer code, String message) {
		HeimdallResponse<?> response = new HeimdallResponse<>(code, message);
		return new Gson().toJson(response);
	}

	public static String toJson(ResponseCode response, UserResponse userResponse) {
		HeimdallResponse<UserResponse> responseMessage = new HeimdallResponse<UserResponse>(userResponse,
				response.getCode(), response.getMessage());
		return new Gson().toJson(responseMessage);
	}
}
