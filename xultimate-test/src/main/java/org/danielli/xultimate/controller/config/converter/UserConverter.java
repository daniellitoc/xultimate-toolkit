package org.danielli.xultimate.controller.config.converter;

import org.danielli.xultimate.model.User;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.math.NumberUtils;
import org.springframework.core.convert.converter.Converter;

public class UserConverter implements Converter<String, User> {

	@Override
	public User convert(String source) {
		User user = new User();
		if (StringUtils.isNotBlank(source)) {
			String[] items = source.split("|");
			user.setName(items[0]);
			user.setId(NumberUtils.createLong(items[1]));
		}
		return user;
	}

}
