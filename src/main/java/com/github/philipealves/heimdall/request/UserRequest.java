package com.github.philipealves.heimdall.request;

import java.io.Serializable;

public class UserRequest implements Serializable {

	private static final long serialVersionUID = -1746801184127557735L;

	private String username;
	private String name;
	private String email;
	private String password;

	public UserRequest() {
		super();
	}

	public UserRequest(String username, String name, String email, String password) {
		super();
		this.username = username;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
