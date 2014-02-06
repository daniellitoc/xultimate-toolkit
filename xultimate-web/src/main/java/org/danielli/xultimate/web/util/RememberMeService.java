package org.danielli.xultimate.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.danielli.xultimate.context.crypto.support.StringStringCryptor;
import org.danielli.xultimate.util.BooleanUtils;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.crypto.DigestUtils;
import org.danielli.xultimate.util.crypto.MessageDigestAlgorithms;
import org.joda.time.DateTime;

public class RememberMeService {

	private static final String SPLIT = ":";
	
	private CookieGenerator rememberMeCookieGenerator;
	
	private StringStringCryptor base64Cryptor;
	
	private String webKeyPassword;
	
	private String requestParameterName;

	/**
	 * 获取用户名。
	 * 
	 * @param request 请求。
	 * @param response 响应。
	 * @return 如果通过验证，返回用户名，否则返回null。
	 */
	public String getRememberMeUserName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Cookie cookie = WebUtils.getCookie(request, rememberMeCookieGenerator.getCookieName());
		if (cookie == null) return null;
		String cookieValue = cookie.getValue();
		if (StringUtils.isEmpty(cookieValue)) {
			rememberMeCookieGenerator.removeCookie(response);
			return null;
		}
		try {
			String[] cookieValues = org.apache.commons.lang3.StringUtils.split(base64Cryptor.decrypt(cookieValue), SPLIT);
			if (org.apache.commons.lang3.ArrayUtils.getLength(cookieValues) != 3) {
				rememberMeCookieGenerator.removeCookie(response);
				return null;
			}
			
			Long expMillSeconds = NumberUtils.createLong(cookieValues[1]);
			if (new DateTime(expMillSeconds).isBeforeNow()) {
				rememberMeCookieGenerator.removeCookie(response);
				return null;
			}
			
			String userName = cookieValues[0];
			
			String password = null;
			if (StringUtils.isEmpty(password)) {
				rememberMeCookieGenerator.removeCookie(response);
				return null;
			}
			
			StringBuilder webKeyBuilder = new StringBuilder();
			webKeyBuilder.append(userName).append(password).append(expMillSeconds).append(webKeyPassword);;
			String webKey = DigestUtils.digest(MessageDigestAlgorithms.SHA_1, webKeyBuilder.toString());
			
			if (!StringUtils.equals(webKey, cookieValues[2])) {
				rememberMeCookieGenerator.removeCookie(response);
				return null;
			}
			return userName;
		} catch (Exception e) {
			rememberMeCookieGenerator.removeCookie(response);
			throw e;
		}
	}
	
	/**
	 * 记录用户。
	 * 
	 * @param request 请求。
	 * @param response 响应。
	 * @param userName 用户名。
	 * @param password 密码。
	 */
	public void doRememberMe(HttpServletRequest request, HttpServletResponse response, String userName, String password) throws Exception {
		if (BooleanUtils.isTrue(BooleanUtils.toBooleanObject(request.getParameter(requestParameterName)))) {
			Integer cookieMaxAge = rememberMeCookieGenerator.getCookieMaxAge();
			Long expMillSeconds = new DateTime().plusSeconds(cookieMaxAge).getMillis();
			StringBuilder webKeyBuilder = new StringBuilder();
			webKeyBuilder.append(userName).append(password).append(expMillSeconds).append(webKeyPassword);
			String webKey = DigestUtils.digest(MessageDigestAlgorithms.SHA_1, webKeyBuilder.toString());
			
			StringBuilder cookieValueBuilder = new StringBuilder();
			cookieValueBuilder.append(userName).append(SPLIT).append(expMillSeconds).append(SPLIT).append(webKey);
			
			String cookieValue = base64Cryptor.encrypt(cookieValueBuilder.toString());
			rememberMeCookieGenerator.addCookie(response, cookieValue);
		}
	}
	
	public void setBase64Cryptor(StringStringCryptor base64Cryptor) {
		this.base64Cryptor = base64Cryptor;
	}

	public void setWebKeyPassword(String webKeyPassword) {
		this.webKeyPassword = webKeyPassword;
	}

	public void setRememberMeCookieGenerator(CookieGenerator rememberMeCookieGenerator) {
		this.rememberMeCookieGenerator = rememberMeCookieGenerator;
	}
}
