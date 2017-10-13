package com.github.philipealves.heimdall.service.impl;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.philipealves.heimdall.model.User;
import com.github.philipealves.heimdall.repository.UserRepository;
import com.github.philipealves.heimdall.request.UserRequest;
import com.github.philipealves.heimdall.response.UserResponse;
import com.github.philipealves.heimdall.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	public UserResponse save(UserRequest request) throws Exception {
		if (StringUtils.isBlank(request.getPassword())) {
			throw new InvalidParameterException("msg.constraint.user.password.null");
		}
		User user = buildFromRequest(request);
		user.setPassword(cripto(user.getPassword()));
		user.setEnabled(Boolean.TRUE);
		return buildResponse(this.userRepository.save(user));
	}

	@Override
	public UserResponse findByUsername(String username) throws Exception {
		return buildResponse(this.userRepository.findByUsername(username));
	}

	@Override
	public List<UserResponse> findAll() throws Exception {
		return buildResponse(this.userRepository.findAll());
	}

	private String cripto(String password) {
		return encoder.encode(password);
	}

	private UserResponse buildResponse(User user) {
		return new UserResponse(user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
				user.isEnabled());
	}

	private List<UserResponse> buildResponse(List<User> users) {
		List<UserResponse> responseList = new ArrayList<>();
		for (User user : users) {
			responseList.add(buildResponse(user));
		}

		return responseList;
	}

	private User buildFromRequest(UserRequest request) {
		return new User(request.getUsername(), request.getName(), request.getEmail(), request.getPassword());
	}

}
