package org.danielli.xultimate.web.util;

import java.util.Date;

import org.danielli.xultimate.util.Assert;
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
 * @since 15 Jun 2013
 */
public class CookieGenerator extends org.springframework.web.util.CookieGenerator {
	
	@SuppressWarnings("unused")
	private static final int COOKIE_MAX_ZERO_TIME = 0;
	private static final int COOKIE_MAX_BROWSER_TIME = -1;
	
	public CookieGenerator(String cookieName) {
		setCookieName(cookieName);
	}

	/**
	 * 设置Cookie失效时间。
	 * 
	 * @param cookieExpireDate Cookie失效时间。
	 */
	public void setCookieMaxAge(Date cookieExpireDate) {
		Duration duration = new Duration(DateTime.now(), new DateTime(cookieExpireDate));
		Assert.isTrue(duration.getStandardSeconds() > 0, "expireDate must be great than now"); 
		super.setCookieMaxAge((int) duration.getStandardSeconds());
	}
	
	/**
	 * 设置Cookie失效时间为当浏览器关闭时。
	 */
	public void setCookExpireOnCloseBrowser() {
		super.setCookieMaxAge(COOKIE_MAX_BROWSER_TIME);
	}
}
