package com.github.philipealves.heimdall.service;

import java.util.List;

import com.github.philipealves.heimdall.model.User;

public interface UserService {

	User save(User user);

	User findByUsername(String username);

	List<User> findAll();

}
