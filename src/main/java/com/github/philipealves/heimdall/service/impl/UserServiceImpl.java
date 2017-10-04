package com.github.philipealves.heimdall.service.impl;

import java.security.InvalidParameterException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.philipealves.heimdall.model.User;
import com.github.philipealves.heimdall.repository.UserRepository;
import com.github.philipealves.heimdall.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	public User save(User user) {
		if (StringUtils.isBlank(user.getPassword())) {
			throw new InvalidParameterException();
		}
		user.setPassword(cripto(user.getPassword()));
		user.setEnabled(Boolean.TRUE);
		return this.userRepository.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return this.userRepository.findByUsername(username);
	}

	@Override
	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	private String cripto(String password) {
		return encoder.encode(password);
	}

}
