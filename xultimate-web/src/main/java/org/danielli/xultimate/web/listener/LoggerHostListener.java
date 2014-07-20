package org.danielli.xultimate.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.danielli.xultimate.web.util.NetworkUtils;
import org.danielli.xultimate.web.util.PropertyCallback;
import org.danielli.xultimate.web.util.WebUtils;
import org.slf4j.MDC;

/**
 * 设置host值到MDC当中，使SLF4j可以使用host值创建日志。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 * @see ServletContextListener
 * @see PropertyCallback
 * @see WebUtils#setProperty(ServletContext, String, String, String, PropertyCallback)
 * @see WebUtils#removeProperty(ServletContext, String, String, RemovePropertyCallback)
 * @see NetworkUtils#getLocalHostAddress()
 * @see MDC
 */
public class LoggerHostListener implements ServletContextListener {
	
	/**
	 * Logger host key parameter at the servlet context level
	 * (i.e. a context-param in {@code web.xml}): "loggerHostKey".
	 */
	public static final String LOGGER_HOST_KEY_PARAM = "loggerHostKey";

	/** Default logger host key: "logger.host" */
	public static final String DEFAULT_LOGGER_HOST_KEY = "logger.host";

	/**
	 * 属性Callback实例。主要提供了属性的添加和删除操作。
	 */
	public final PropertyCallback propertyCallback = new PropertyCallback() {
		
		@Override
		public void set(String key, String value) {
			MDC.put(key, value);
		}
		
		@Override
		public String get(String key) {
			return MDC.get(key);
		}
		
		@Override
		public void remove(String key) {
			MDC.remove(key);
		}
	};
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		WebUtils.setProperty(servletContext, LOGGER_HOST_KEY_PARAM, DEFAULT_LOGGER_HOST_KEY, NetworkUtils.getLocalHostAddress(), propertyCallback);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		WebUtils.removeProperty(servletContext, LOGGER_HOST_KEY_PARAM, DEFAULT_LOGGER_HOST_KEY, propertyCallback);
	}
}
