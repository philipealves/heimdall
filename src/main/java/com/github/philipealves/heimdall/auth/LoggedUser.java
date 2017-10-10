package com.github.philipealves.heimdall.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.github.philipealves.heimdall.response.UserResponse;

public class LoggedUser extends User {

	private static final long serialVersionUID = 1186233186547816504L;

	private String name;
	private String email;
	private boolean enabled;

	public LoggedUser(UserResponse user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getEmail(), user.getPassword(), authorities);
		this.name = user.getName();
		this.email = user.getEmail();
		this.enabled = user.isEnabled();
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
