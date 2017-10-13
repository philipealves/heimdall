package com.github.philipealves.heimdall.service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.philipealves.heimdall.model.User;
import com.github.philipealves.heimdall.repository.UserRepository;
import com.github.philipealves.heimdall.request.UserRequest;
import com.github.philipealves.heimdall.response.UserResponse;
import com.github.philipealves.heimdall.service.impl.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private BCryptPasswordEncoder encoder;

	@InjectMocks
	private UserService userService = new UserServiceImpl();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCrud() throws Exception {
		User user = new User(RandomStringUtils.randomAlphanumeric(10), "username", "name", "email@email.com",
				"password", Boolean.TRUE);
		Mockito.when(this.userRepository.save(Mockito.any(User.class))).thenReturn(user);
		Mockito.when(this.userRepository.findByUsername(Mockito.any(String.class))).thenReturn(user);
		Mockito.when(this.userRepository.findAll()).thenReturn(Arrays.asList(user));
		Mockito.when(this.encoder.encode(Mockito.anyString())).thenReturn(user.getPassword());

		UserRequest userRequest = new UserRequest("username", "name", "email@email.com", "password");
		assertNotNull(userRequest);

		UserResponse userResponse = this.userService.save(userRequest);
		assertNotNull(userResponse);
		verify(userRepository).save(Mockito.any(User.class));

		UserResponse testUser = this.userService.findByUsername("username");
		assertEquals(testUser, userResponse);

		List<UserResponse> testList = this.userService.findAll();
		assertThat(testList, not(IsEmptyCollection.empty()));
		verify(userRepository).findAll();
	}

	@Test
	public void testPasswordNull() throws Exception {
		try {
			User user = new User(RandomStringUtils.randomAlphanumeric(10), "username", "name", "email@email.com",
					"password", Boolean.TRUE);
			Mockito.when(this.userRepository.save(Mockito.any(User.class))).thenReturn(user);

			UserRequest userRequest = new UserRequest("username", "name", "email@email.com", StringUtils.EMPTY);
			assertNotNull(userRequest);

			this.userService.save(userRequest);
			fail();
		} catch (InvalidParameterException e) {
			String msg = "msg.constraint.user.password.null";
			Assert.assertThat(e.getMessage(), containsString(msg));
		}
	}
}
