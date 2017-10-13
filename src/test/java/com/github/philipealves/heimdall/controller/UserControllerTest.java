package com.github.philipealves.heimdall.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.philipealves.heimdall.data.template.UserRequestTemplate;
import com.github.philipealves.heimdall.data.template.UserResponseTemplate;
import com.github.philipealves.heimdall.data.template.UserTemplate;
import com.github.philipealves.heimdall.request.UserRequest;
import com.github.philipealves.heimdall.response.UserResponse;
import com.github.philipealves.heimdall.service.UserService;
import com.github.philipealves.heimdall.utils.HeimdallResponse;
import com.github.philipealves.heimdall.utils.ResponseCode;
import com.google.gson.Gson;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	private static final String URL = "/users/";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@MockBean
	private UserService userService;

	@Before
	public void setUp() {
		FixtureFactoryLoader.loadTemplates(UserTemplate.class.getPackage().getName());
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void saveUser() throws Exception {
		UserResponse response = Fixture.from(UserResponse.class).gimme(UserResponseTemplate.NEW);
		UserRequest request = Fixture.from(UserRequest.class).gimme(UserRequestTemplate.NEW);
		Mockito.when(userService.save(Mockito.any(UserRequest.class))).thenReturn(response);

		MvcResult result = mockMvc
								.perform(MockMvcRequestBuilders.post(URL)
								.contentType(MediaType.APPLICATION_JSON_UTF8)
								.accept(MediaType.APPLICATION_JSON_UTF8)
								.with(user("user"))
								.content(new Gson().toJson(request))).andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);

		verify(userService).save(Mockito.any(UserRequest.class));

		HeimdallResponse<?> resultUser = new Gson().fromJson(result.getResponse().getContentAsString(),
				HeimdallResponse.class);
		assertNotNull(resultUser);
		assertNotNull(resultUser.getResult());
		assertThat(resultUser.getCode(), equalTo(ResponseCode.SUCCESS.getCode()));
	}
	
	@Test
	public void saveUserFail() throws Exception {
		UserRequest request = Fixture.from(UserRequest.class).gimme(UserRequestTemplate.NEW);
		Mockito.when(userService.save(Mockito.any(UserRequest.class))).thenThrow(new Exception());

		MvcResult result = mockMvc
								.perform(MockMvcRequestBuilders.post(URL)
								.contentType(MediaType.APPLICATION_JSON_UTF8)
								.accept(MediaType.APPLICATION_JSON_UTF8)
								.with(user("user"))
								.content(new Gson().toJson(request))).andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);

		verify(userService).save(Mockito.any(UserRequest.class));

		HeimdallResponse<?> resultUser = new Gson().fromJson(result.getResponse().getContentAsString(),
				HeimdallResponse.class);
		assertNotNull(resultUser);
		assertNull(resultUser.getResult());
		assertThat(resultUser.getCode(), equalTo(ResponseCode.ERROR.getCode()));
	}
	
	@Test
	public void getUser() throws Exception {
		UserResponse response = Fixture.from(UserResponse.class).gimme(UserResponseTemplate.NEW);
		Mockito.when(userService.findByUsername(Mockito.anyString())).thenReturn(response);

		MvcResult result = mockMvc
								.perform(MockMvcRequestBuilders.get(URL + "/" + response.getUsername())
								.with(user("user"))
								.accept(MediaType.APPLICATION_JSON_UTF8))
								.andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);

		verify(userService).findByUsername(Mockito.anyString());

		HeimdallResponse<?> resultUser = new Gson().fromJson(result.getResponse().getContentAsString(),
				HeimdallResponse.class);
		assertNotNull(resultUser);
		assertNotNull(resultUser.getResult());
		assertThat(resultUser.getCode(), equalTo(ResponseCode.SUCCESS.getCode()));
	}
	
	@Test
	public void getUserFail() throws Exception {
		Mockito.when(userService.findByUsername(Mockito.anyString())).thenThrow(new Exception());

		MvcResult result = mockMvc
								.perform(MockMvcRequestBuilders.get(URL + RandomStringUtils.randomAlphanumeric(10))
								.with(user("user")).accept(MediaType.APPLICATION_JSON_UTF8))
								.andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);

		verify(userService).findByUsername(Mockito.anyString());

		HeimdallResponse<?> resultUser = new Gson().fromJson(result.getResponse().getContentAsString(),
				HeimdallResponse.class);
		assertNotNull(resultUser);
		assertNull(resultUser.getResult());
		assertThat(resultUser.getCode(), equalTo(ResponseCode.ERROR.getCode()));
	}

	@Test
	public void getAllUsers() throws Exception {
		List<UserResponse> users = Fixture.from(UserResponse.class).gimme(50, UserResponseTemplate.NEW);
		Mockito.when(userService.findAll()).thenReturn(users);

		MvcResult result = mockMvc
								.perform(MockMvcRequestBuilders.get(URL).accept(MediaType.APPLICATION_JSON_UTF8)
								.with(user("user")))
								.andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);

		verify(userService).findAll();

		HeimdallResponse<?> resultUsers = new Gson().fromJson(result.getResponse().getContentAsString(),
				HeimdallResponse.class);
		assertNotNull(resultUsers);
		assertNotNull(resultUsers.getResult());
		assertThat(resultUsers.getCode(), equalTo(ResponseCode.SUCCESS.getCode()));
	}
	
	@Test
	public void getAllUsersFail() throws Exception {
		Mockito.when(userService.findAll()).thenThrow(new Exception());

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL).accept(MediaType.APPLICATION_JSON_UTF8).with(user("user")))
				.andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);

		verify(userService).findAll();

		HeimdallResponse<?> resultUsers = new Gson().fromJson(result.getResponse().getContentAsString(),
				HeimdallResponse.class);
		assertNotNull(resultUsers);
		assertNull(resultUsers.getResult());
		assertThat(resultUsers.getCode(), equalTo(ResponseCode.ERROR.getCode()));
	}
}
