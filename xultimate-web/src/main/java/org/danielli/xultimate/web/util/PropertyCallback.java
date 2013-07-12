package org.danielli.xultimate.web.util;


/**
 * 属性值回调类。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 * @see WebUtils#setProperty(javax.servlet.ServletContext, String, String, String, SetPropertyCallback)
 */
public interface PropertyCallback {
	
	/**
	 * 获取属性值。
	 * 
	 * @param key 与属性值对应的属性键。
	 * @return 属性值。
	 */
	String get(String key);
	
	/**
	 * 设置属性值。
	 * 
	 * @param key 属性键。
	 * @param value 属性值。
	 */
	void set(String key, String value);
	
	/**
	 * 删除属性值。
	 * 
	 * @param key 被删除的属性键。
	 */
	void remove(String key);
}
