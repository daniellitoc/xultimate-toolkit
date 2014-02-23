package org.danielli.xultimate.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.danielli.xultimate.context.crypto.support.StringStringCryptor;
import org.danielli.xultimate.util.ArrayUtils;
import org.danielli.xultimate.util.BooleanUtils;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.crypto.DigestUtils;
import org.danielli.xultimate.util.crypto.MessageDigestAlgorithms;
import org.danielli.xultimate.util.math.NumberUtils;
import org.joda.time.DateTime;

/**
 * 实现自动登陆功能。
 *
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public class RememberMeService {
	/** 信息分隔符 */
	protected static final String SPLIT = ":";

	/** Cookie生成器 */
	private CookieGenerator rememberMeCookieGenerator;
	/** Cookie Value加密器 */
	private StringStringCryptor cookieValueCryptor;
	/** 私有密码 */
	private String webKeyPassword;
	/** 请求参数 */
	private String requestParameterName;
	/** 密码回调器 */
	private PasswordCallback passwordCallback;

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
			String[] cookieValues = StringUtils.split(cookieValueCryptor.decrypt(cookieValue), SPLIT);
			if (ArrayUtils.getLength(cookieValues) != 3) {
				rememberMeCookieGenerator.removeCookie(response);
				return null;
			}
			
			Long expMillSeconds = NumberUtils.createLong(cookieValues[1]);
			if (new DateTime(expMillSeconds).isBeforeNow()) {
				rememberMeCookieGenerator.removeCookie(response);
				return null;
			}
			
			String userName = cookieValues[0];
			
			String password = passwordCallback.getPassword(userName);
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
			if (cookieMaxAge != null) {
				Long expMillSeconds = new DateTime().plusSeconds(cookieMaxAge).getMillis();
				
				StringBuilder webKeyBuilder = new StringBuilder();
				webKeyBuilder.append(userName).append(password).append(expMillSeconds).append(webKeyPassword);
				String webKey = DigestUtils.digest(MessageDigestAlgorithms.SHA_1, webKeyBuilder.toString());
				
				StringBuilder cookieValueBuilder = new StringBuilder();
				cookieValueBuilder.append(userName).append(SPLIT).append(expMillSeconds).append(SPLIT).append(webKey);
				
				String cookieValue = cookieValueCryptor.encrypt(cookieValueBuilder.toString());
				rememberMeCookieGenerator.addCookie(response, cookieValue);
			}
		}
	}

	/**
	 * 设置Cookie生成器。
	 * @param rememberMeCookieGenerator Cookie生成器。
	 */
	public void setRememberMeCookieGenerator(CookieGenerator rememberMeCookieGenerator) {
		this.rememberMeCookieGenerator = rememberMeCookieGenerator;
	}
	
	/**
	 * 设置Cookie Value加密器。
	 * @param cookieValueCryptor Cookie Value加密器。
	 */
	public void setCookieValueCryptor(StringStringCryptor cookieValueCryptor) {
		this.cookieValueCryptor = cookieValueCryptor;
	}
	
	/**
	 * 私有密码。
	 * @param webKeyPassword 私有密码。
	 */
	public void setWebKeyPassword(String webKeyPassword) {
		this.webKeyPassword = webKeyPassword;
	}

	/**
	 * 请求参数。
	 * @param requestParameterName 请求参数。
	 */
	public void setRequestParameterName(String requestParameterName) {
		this.requestParameterName = requestParameterName;
	}
	
	/**
	 * 密码回调器。
	 * @param passwordCallback 密码回调器。
	 */
	public void setPasswordCallback(PasswordCallback passwordCallback) {
		this.passwordCallback = passwordCallback;
	}
}
