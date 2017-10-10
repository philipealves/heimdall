package com.github.philipealves.heimdall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.philipealves.heimdall.request.UserRequest;
import com.github.philipealves.heimdall.response.UserResponse;
import com.github.philipealves.heimdall.service.UserService;
import com.github.philipealves.heimdall.utils.HeimdallResponse;
import com.github.philipealves.heimdall.utils.ResponseCode;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST)
	public HeimdallResponse<UserResponse> saveUser(@RequestBody UserRequest user) {
		try {
			return new HeimdallResponse<>(this.userService.save(user), ResponseCode.SUCCESS.getCode(),
					ResponseCode.SUCCESS.getMessage());
		} catch (Exception e) {
			return new HeimdallResponse<>(ResponseCode.ERROR.getCode(), e.getMessage());
		}
	}

	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public HeimdallResponse<UserResponse> getUser(@PathVariable("username") String username) {
		try {
			return new HeimdallResponse<>(this.userService.findByUsername(username), ResponseCode.SUCCESS.getCode(),
					ResponseCode.SUCCESS.getMessage());
		} catch (Exception e) {
			return new HeimdallResponse<>(ResponseCode.ERROR.getCode(), e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public HeimdallResponse<List<UserResponse>> getUsers() {
		try {
			return new HeimdallResponse<>(this.userService.findAll(), ResponseCode.SUCCESS.getCode(),
					ResponseCode.SUCCESS.getMessage());
		} catch (Exception e) {
			return new HeimdallResponse<>(ResponseCode.ERROR.getCode(), e.getMessage());
		}
	}

}
