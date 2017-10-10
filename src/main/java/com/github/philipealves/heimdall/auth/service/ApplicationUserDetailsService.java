package com.github.philipealves.heimdall.auth.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.github.philipealves.heimdall.auth.LoggedUser;
import com.github.philipealves.heimdall.response.UserResponse;
import com.github.philipealves.heimdall.service.UserService;

@Component
public class ApplicationUserDetailsService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(ApplicationUserDetailsService.class);

	@Autowired
	private UserService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		try {

			/* consulta o usuario pelo e-mail */
			UserResponse user = this.usuarioService.findByUsername(username);
			if (user != null) {
				if (user.isEnabled()) {

					/* recupera as permissoes de acesso do usuario */
					List<GrantedAuthority> authorities = getAuthorities(user);
					return new LoggedUser(user, authorities);

				} else {

					/* usuario inativo */
					throw new AuthenticationServiceException("Usuário inativo.");

				}
			}

		} catch (AuthenticationServiceException e) {
			throw e;
		} catch (Exception e) {
			log.error("Falha ao realizar login", e);
		}

		throw new UsernameNotFoundException("Usuário não encontrado");

	}

	/**
	 * Consulta as permissoes do usuario informado como parametro.
	 * 
	 * @param user
	 * @return {@link List}
	 */
	private List<GrantedAuthority> getAuthorities(UserResponse user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ADMIN"));
		return authorities;
	}

}
