package org.danielli.xultimate.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 * Helper class for cookie generation, carrying cookie descriptor settings
 * as bean properties and being able to add and remove cookie to/from a
 * given response.
 *
 * <p>Can serve as base class for components that generate specific cookies,
 * like CookieLocaleResolcer and CookieThemeResolver.
 *
 * @author Daniel Li
 * @author Juergen Hoeller
 * @since 15 Jun 2013
 * @see #addCookie
 * @see #removeCookie
 */
public class CookieGenerator extends org.springframework.web.util.CookieGenerator {

	private static final int COOKIE_MAX_ZERO_TIME = 0;
	private static final int COOKIE_MAX_BROWSER_TIME = -1;
	
	private DateTime cookieExpireDate;
	
	public CookieGenerator(String cookieName) {
		setCookieName(cookieName);
	}

	/**
	 * 设置Cookie失效时间。
	 * 
	 * @param cookieMaxAge Cookie失效时间。
	 */
	public void setCookieMaxAge(DateTime cookieMaxAge) {
		this.cookieExpireDate = cookieMaxAge;
	}
	

	@Override
	public Integer getCookieMaxAge() {
		Integer maxAge = super.getCookieMaxAge();
		if (maxAge != null) {
			return maxAge;
		} else if (this.cookieExpireDate != null) {
			Duration duration = new Duration(DateTime.now(), cookieExpireDate);
			if (duration.getStandardSeconds() <= 0) {
				if (logger.isWarnEnabled()) {
					logger.warn("expireDate must be great than now, ignore setMaxAge(int expiry)");
				}
			} else {
				return (int) duration.getStandardSeconds();
			}
		}
		return null;
	}
	
	@Override
	public void addCookie(HttpServletResponse response, String cookieValue) {
		Cookie cookie = createCookie(cookieValue);
		Integer maxAge = getCookieMaxAge();
		if (maxAge != null) {
			cookie.setMaxAge(maxAge);
		}
		if (isCookieSecure()) {
			cookie.setSecure(true);
		}
		if (isCookieHttpOnly()) {
			cookie.setHttpOnly(true);
		}
		response.addCookie(cookie);
		if (logger.isDebugEnabled()) {
			logger.debug("Added cookie with name [" + getCookieName() + "] and value [" + cookieValue + "]");
		}
	}
	
	@Override
	public void removeCookie(HttpServletResponse response) {
		Cookie cookie = createCookie("");
		cookie.setMaxAge(COOKIE_MAX_ZERO_TIME);
		response.addCookie(cookie);
		if (logger.isDebugEnabled()) {
			logger.debug("Removed cookie with name [" + getCookieName() + "]");
		}
	}
	
	/**
	 * 设置Cookie失效时间为当浏览器关闭时。
	 */
	public void setCookExpireOnCloseBrowser() {
		super.setCookieMaxAge(COOKIE_MAX_BROWSER_TIME);
	}
}
