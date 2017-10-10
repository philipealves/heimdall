package com.github.philipealves.heimdall.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.github.philipealves.heimdall.auth.handler.HeimdallAuthenticationFailureHandler;
import com.github.philipealves.heimdall.auth.handler.HeimdallAuthenticationSuccessHandler;
import com.github.philipealves.heimdall.auth.service.ApplicationUserDetailsService;
import com.github.philipealves.heimdall.filter.JWTAuthenticationFilter;
import com.github.philipealves.heimdall.filter.JwtAuthenticationProcessingFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String URL_LOGIN = "/login";

	@Autowired
	private ApplicationUserDetailsService userDetailsService;

	@Autowired
	private HeimdallAuthenticationSuccessHandler successHandler;

	@Autowired
	private HeimdallAuthenticationFailureHandler failureHandler;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable()
				.authorizeRequests()
					.antMatchers(HttpMethod.POST, URL_LOGIN).permitAll()
					.antMatchers(HttpMethod.POST, "/users").permitAll()
					.anyRequest().authenticated()
				.and()
				.addFilterBefore(buildJwtFilter(URL_LOGIN), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	private JwtAuthenticationProcessingFilter buildJwtFilter(String loginUrl) throws Exception {
		return new JwtAuthenticationProcessingFilter(loginUrl, authenticationManager(), successHandler, failureHandler);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}