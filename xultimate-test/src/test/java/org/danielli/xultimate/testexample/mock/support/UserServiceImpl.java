package org.danielli.xultimate.testexample.mock.support;

import org.danielli.xultimate.util.ArrayUtils;

public class UserServiceImpl implements UserService {

	private String[] existsUsernames = { "daniellitoc", "danielli", "daniel" };
	
	@Override
	public User getByUsername(String username) {
		boolean result = ArrayUtils.contains(existsUsernames, username);
		if (result) {
			return new User(username);
		} else {
			return null;
		}
	}
	
	@Override
	public void deleteByUsername(String username) {
		ArrayUtils.removeElement(existsUsernames, username);
	}
}
