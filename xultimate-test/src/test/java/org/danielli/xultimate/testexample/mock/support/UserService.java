package org.danielli.xultimate.testexample.mock.support;

public interface UserService {

	User getByUsername(String username);
	
	void deleteByUsername(String username);
}
