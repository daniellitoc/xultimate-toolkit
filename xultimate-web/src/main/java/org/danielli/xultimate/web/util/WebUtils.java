package org.danielli.xultimate.web.util;

import javax.servlet.ServletContext;

import org.danielli.xultimate.util.Assert;
import org.danielli.xultimate.util.StringUtils;

/**
 * Web工具类。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 * @see ServletContext
 * @see PropertyCallback
 */
public class WebUtils {
	
	/**
	 * 调用Callback设置属性。
	 * 
	 * @param servletContext	Servlet上下文。
	 * @param keyParam	Servlet上下文中属性键的名称。
	 * @param defaultkey	默认的属性键。如果keyParam对应的属性键不存在，使用此值。
	 * @param value	属性值。
	 * @param callback	回调方法。
	 */
	public static void setProperty(ServletContext servletContext, String keyParam, String defaultkey, String value, PropertyCallback callback) {
		Assert.notNull(servletContext, "servletContext must not be null");
		Assert.notNull(value, "value must not be null");
		Assert.notNull(callback, "callback must not be null");
		String param = servletContext.getInitParameter(keyParam);
		String key = param == null ? defaultkey : param;
		String oldValue = callback.get(key);
		if (oldValue != null && !StringUtils.equals(oldValue, value)) {
			throw new IllegalStateException(
				new StringBuilder().append("property already set to different value: '").append(key).append("' = [")
					.append(oldValue).append("] instead of [").append(value).append("] - ").append("Choose unique values for the '")
					.append(keyParam).append("' context-param in your web.xml files!").toString());
		}
		callback.set(key, value);
	}
	
	/**
	 * 调用Callback删除属性。
	 * 
	 * @param servletContext	Servlet上下文。
	 * @param keyParam	Servlet上下文中属性键的名称。
	 * @param defaultkey	默认的属性键。如果keyParam对应的属性键不存在，使用此值。
	 * @param callback	回调方法。
	 */
	public static void removeProperty(ServletContext servletContext, String keyParam, String defaultkey, PropertyCallback callback) {
		Assert.notNull(servletContext, "servletContext must not be null");
		Assert.notNull(callback, "callback must not be null");
		String param = servletContext.getInitParameter(keyParam);
		String key = param == null ? defaultkey : param;
		callback.remove(key);
	}
	
}
