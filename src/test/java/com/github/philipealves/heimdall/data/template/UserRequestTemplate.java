package com.github.philipealves.heimdall.data.template;

import com.github.philipealves.heimdall.request.UserRequest;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class UserRequestTemplate implements TemplateLoader {

	public static String NEW = "new";
	public static String NEW_WITH_ID = "newWithId";
	public static String NEW_INACTIVE = "newInactive";

	@Override
	public void load() {

		Fixture.of(UserRequest.class).addTemplate(NEW, new Rule() {
			{
				add("name", name());
				add("username", "${name}");
				add("email", "${name}@nextcar.com.br");
				add("password", regex("[a-zA-Z]\\w{3,14}"));
			}
		});

		Fixture.of(UserRequest.class).addTemplate(NEW_INACTIVE, new Rule() {
			{
				add("name", name());
				add("username", "${name}");
				add("email", "${name}@nextcar.com.br");
				add("password", regex("[a-zA-Z]\\w{3,14}"));
			}
		});
	}
}
