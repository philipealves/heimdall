package com.github.philipealves.heimdall.service;

import java.util.List;

import com.github.philipealves.heimdall.request.UserRequest;
import com.github.philipealves.heimdall.response.UserResponse;

public interface UserService {

	UserResponse save(UserRequest user);

	UserResponse findByUsername(String username);

	List<UserResponse> findAll();

}
