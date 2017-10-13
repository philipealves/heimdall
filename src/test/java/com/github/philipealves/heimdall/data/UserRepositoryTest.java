package com.github.philipealves.heimdall.data;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.philipealves.heimdall.MongoConfiguration;
import com.github.philipealves.heimdall.data.template.UserTemplate;
import com.github.philipealves.heimdall.model.User;
import com.github.philipealves.heimdall.repository.UserRepository;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootConfiguration
@Import(MongoConfiguration.class)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Before
	public void setUp() {
		FixtureFactoryLoader.loadTemplates(UserTemplate.class.getPackage().getName());
	}

	@Test
	public void testEqualsAndHashCode() {
		User dev1 = Fixture.from(User.class).gimme(UserTemplate.NEW_WITH_ID);
		User dev2 = new User(dev1.getId(), dev1.getUsername(), dev1.getName(), dev1.getEmail(), dev1.getPassword(),
				dev1.isEnabled());
		Assert.assertEquals(dev1.hashCode(), dev2.hashCode());
		Assert.assertEquals(dev1, dev1);
		Assert.assertNotEquals(dev1, null);
		Assert.assertNotEquals(dev1, StringUtils.EMPTY);
		Assert.assertEquals(dev1, dev2);
		dev1.setUsername("newUsername");
		Assert.assertNotEquals(dev1, dev2);
		dev1.setUsername(null);
		Assert.assertNotEquals(dev1, dev2);
		Assert.assertNotEquals(dev1.hashCode(), dev2.hashCode());
	}

	/**
	 * Realiza um teste das funcionalidades CRUD... relevante para todas as
	 * entidades.
	 */
	@Test
	public void testCrud() {

		User user = Fixture.from(User.class).gimme(UserTemplate.NEW);

		/*
		 * verifica se os campos autogerados (autoincremento por exemplo) sao
		 * nulos.
		 */
		Assert.assertNotNull(user);

		/*
		 * insere a entidade e verifica se os campos autogerados nao sao nulos.
		 */
		user = this.userRepository.save(user);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getUsername());

		/*
		 * verifica se o registro consultado tem o valor esperado (metodo equals
		 * precisa ter sido implementado para a entidade).
		 */
		User testUser = this.userRepository.findByUsername(user.getUsername());
		Assert.assertEquals(testUser, user);

		/*
		 * altera a entidade e verifica se a alteracao foi persistida.
		 */
		user.setUsername("newUsername");
		user = this.userRepository.save(user);
		testUser = this.userRepository.findByUsername(user.getUsername());

		Assert.assertEquals(user, testUser);

		/*
		 * remove a entidade e verifica se ela foi removida.
		 */
		this.userRepository.delete(user);
		user = this.userRepository.findByUsername(user.getUsername());
		Assert.assertNull(user);
	}
}


