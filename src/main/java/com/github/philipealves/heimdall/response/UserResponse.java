package com.github.philipealves.heimdall.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserResponse implements Serializable {

	private static final long serialVersionUID = -5568229094752993984L;

	private String username;
	@JsonIgnore
	private String password;
	private String name;
	private String email;
	private boolean enabled;

	public UserResponse() {
		super();
	}

	public UserResponse(String username, String password, String name, String email, boolean enabled) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
		this.enabled = enabled;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
