package com.github.philipealves.heimdall.data.template;

import com.github.philipealves.heimdall.model.User;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class UserTemplate implements TemplateLoader {

	public static String NEW = "new";
	public static String NEW_WITH_ID = "newWithId";
	public static String NEW_INACTIVE = "newInactive";

	@Override
	public void load() {

		Fixture.of(User.class).addTemplate(NEW, new Rule() {{
				add("id", null);
				add("name", name());
				add("username", "${name}");
				add("email", "${name}@nextcar.com.br");
				add("password", regex("[a-zA-Z]\\w{3,14}"));
				add("enabled", Boolean.TRUE);
		}});

		Fixture.of(User.class).addTemplate(NEW_WITH_ID, new Rule() {{
				add("id", regex("[a-z0-9]\\w{3,14}"));
				add("name", name());
				add("username", "${name}");
				add("email", "${name}@nextcar.com.br");
				add("password", regex("[a-zA-Z]\\w{3,14}"));
				add("enabled", Boolean.TRUE);
		}});

		Fixture.of(User.class).addTemplate(NEW_INACTIVE, new Rule() {{
				add("id", regex("[a-z0-9]\\w{3,14}"));
				add("name", name());
				add("username", "${name}");
				add("email", "${name}@nextcar.com.br");
				add("password", regex("[a-zA-Z]\\w{3,14}"));
				add("enabled", Boolean.TRUE);
		}});
	}
}
