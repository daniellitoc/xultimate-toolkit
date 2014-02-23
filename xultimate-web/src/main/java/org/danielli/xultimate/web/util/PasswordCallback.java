package org.danielli.xultimate.web.util;

/**
 * 密码回调类。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public interface PasswordCallback {

	/**
	 * 通过用户名获取密码。
	 * 
	 * @param userName 用户名。
	 * @return 密码。
	 */
	String getPassword(String userName);
}
