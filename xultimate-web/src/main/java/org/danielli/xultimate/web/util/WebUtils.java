package org.danielli.xultimate.web.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.iterators.EnumerationIterator;
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
	
	/**
	 * Return the temporary directory for the current web application,
	 * as provided by the servlet container.
	 * @param servletContext the servlet context of the web application
	 * @return the File representing the temporary directory
	 */
	public static File getTempDir(ServletContext servletContext) {
		return org.springframework.web.util.WebUtils.getTempDir(servletContext);
	}
	
	/**
	 * Return the real path of the given path within the web application,
	 * as provided by the servlet container.
	 * <p>Prepends a slash if the path does not already start with a slash,
	 * and throws a FileNotFoundException if the path cannot be resolved to
	 * a resource (in contrast to ServletContext's {@code getRealPath},
	 * which returns null).
	 * @param servletContext the servlet context of the web application
	 * @param path the path within the web application
	 * @return the corresponding real path
	 * @throws FileNotFoundException if the path cannot be resolved to a resource
	 * @see javax.servlet.ServletContext#getRealPath
	 */
	public static String getRealPath(ServletContext servletContext, String path) throws FileNotFoundException {
		return org.springframework.web.util.WebUtils.getRealPath(servletContext, path);
	}
	
	/**
	 * Determine the session id of the given request, if any.
	 * @param request current HTTP request
	 * @return the session id, or {@code null} if none
	 */
	public static String getSessionId(HttpServletRequest request) {
		return org.springframework.web.util.WebUtils.getSessionId(request);
	}
	
	/**
	 * Check the given request for a session attribute of the given name.
	 * Returns null if there is no session or if the session has no such attribute.
	 * Does not create a new session if none has existed before!
	 * @param request current HTTP request
	 * @param name the name of the session attribute
	 * @return the value of the session attribute, or {@code null} if not found
	 */
	public static Object getSessionAttribute(HttpServletRequest request, String name) {
		return org.springframework.web.util.WebUtils.getSessionAttribute(request, name);
	}
	
	/**
	 * Check the given request for a session attribute of the given name.
	 * Throws an exception if there is no session or if the session has no such
	 * attribute. Does not create a new session if none has existed before!
	 * @param request current HTTP request
	 * @param name the name of the session attribute
	 * @return the value of the session attribute, or {@code null} if not found
	 * @throws IllegalStateException if the session attribute could not be found
	 */
	public static Object getRequiredSessionAttribute(HttpServletRequest request, String name) {
		return org.springframework.web.util.WebUtils.getRequiredSessionAttribute(request, name);
	}
	
	/**
	 * Set the session attribute with the given name to the given value.
	 * Removes the session attribute if value is null, if a session existed at all.
	 * Does not create a new session if not necessary!
	 * @param request current HTTP request
	 * @param name the name of the session attribute
	 * @param value the value of the session attribute
	 */
	public static void setSessionAttribute(HttpServletRequest request, String name, Object value) {
		org.springframework.web.util.WebUtils.setSessionAttribute(request, name, value);
	}
	
	/**
	 * Get the specified session attribute, creating and setting a new attribute if
	 * no existing found. The given class needs to have a public no-arg constructor.
	 * Useful for on-demand state objects in a web tier, like shopping carts.
	 * @param session current HTTP session
	 * @param name the name of the session attribute
	 * @param clazz the class to instantiate for a new attribute
	 * @return the value of the session attribute, newly created if not found
	 * @throws IllegalArgumentException if the session attribute could not be instantiated
	 */
	public static Object getOrCreateSessionAttribute(HttpSession session, String name, Class<?> clazz) throws IllegalArgumentException {
		return org.springframework.web.util.WebUtils.getOrCreateSessionAttribute(session, name, clazz);
	}
	
	/**
	 * Determine whether the given request is an include request,
	 * that is, not a top-level HTTP request coming in from the outside.
	 * <p>Checks the presence of the "javax.servlet.include.request_uri"
	 * request attribute. Could check any request attribute that is only
	 * present in an include request.
	 * @param request current servlet request
	 * @return whether the given request is an include request
	 */
	public static boolean isIncludeRequest(ServletRequest request) {
		return org.springframework.web.util.WebUtils.isIncludeRequest(request);
	}
	
	/**
	 * Expose the Servlet spec's error attributes as {@link javax.servlet.http.HttpServletRequest}
	 * attributes under the keys defined in the Servlet 2.3 specification, for error pages that
	 * are rendered directly rather than through the Servlet container's error page resolution:
	 * {@code javax.servlet.error.status_code},
	 * {@code javax.servlet.error.exception_type},
	 * {@code javax.servlet.error.message},
	 * {@code javax.servlet.error.exception},
	 * {@code javax.servlet.error.request_uri},
	 * {@code javax.servlet.error.servlet_name}.
	 * <p>Does not override values if already present, to respect attribute values
	 * that have been exposed explicitly before.
	 * <p>Exposes status code 200 by default. Set the "javax.servlet.error.status_code"
	 * attribute explicitly (before or after) in order to expose a different status code.
	 * @param request current servlet request
	 * @param ex the exception encountered
	 * @param servletName the name of the offending servlet
	 */
	public static void exposeErrorRequestAttributes(HttpServletRequest request, Throwable ex, String servletName) {
		org.springframework.web.util.WebUtils.exposeErrorRequestAttributes(request, ex, servletName);
	}
	
	/**
	 * Expose the specified request attribute if not already present.
	 * @param request current servlet request
	 * @param name the name of the attribute
	 * @param value the suggested value of the attribute
	 */
	public static void exposeRequestAttributeIfNotPresent(ServletRequest request, String name, Object value) {
		if (request.getAttribute(name) == null) {
			request.setAttribute(name, value);
		}
	}

	/**
	 * Clear the Servlet spec's error attributes as {@link javax.servlet.http.HttpServletRequest}
	 * attributes under the keys defined in the Servlet 2.3 specification:
	 * {@code javax.servlet.error.status_code},
	 * {@code javax.servlet.error.exception_type},
	 * {@code javax.servlet.error.message},
	 * {@code javax.servlet.error.exception},
	 * {@code javax.servlet.error.request_uri},
	 * {@code javax.servlet.error.servlet_name}.
	 * @param request current servlet request
	 */
	public static void clearErrorRequestAttributes(HttpServletRequest request) {
		org.springframework.web.util.WebUtils.clearErrorRequestAttributes(request);
	}

	/**
	 * Expose the given Map as request attributes, using the keys as attribute names
	 * and the values as corresponding attribute values. Keys need to be Strings.
	 * @param request current HTTP request
	 * @param attributes the attributes Map
	 */
	public static void exposeRequestAttributes(ServletRequest request, Map<String, ?> attributes) {
		org.springframework.web.util.WebUtils.exposeRequestAttributes(request, attributes);
	}
	
	/**
	 * Retrieve the first cookie with the given name. Note that multiple
	 * cookies can have the same name but different paths or domains.
	 * @param request current servlet request
	 * @param name cookie name
	 * @return the first cookie with the given name, or {@code null} if none is found
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
    	return org.springframework.web.util.WebUtils.getCookie(request, name);
    }
	
	/**
	 * Extract the URL filename from the given request URL path.
	 * Correctly resolves nested paths such as "/products/view.html" as well.
	 * @param urlPath the request URL path (e.g. "/index.html")
	 * @return the extracted URI filename (e.g. "index")
	 */
	public static String extractFilenameFromUrlPath(String urlPath) {
		return org.springframework.web.util.WebUtils.extractFilenameFromUrlPath(urlPath);
	}

	/**
	 * Extract the full URL filename (including file extension) from the given request URL path.
	 * Correctly resolves nested paths such as "/products/view.html" as well.
	 * @param urlPath the request URL path (e.g. "/products/index.html")
	 * @return the extracted URI filename (e.g. "index.html")
	 */
	public static String extractFullFilenameFromUrlPath(String urlPath) {
		return org.springframework.web.util.WebUtils.extractFullFilenameFromUrlPath(urlPath);
	}
	

	/** 包含一个URL，用户从该URL代表的页面出发访问当前请求的页面 */
	public static final String REFERER = "Referer";
	
	public static final String ACCEPT_ENCODING = "Accept-Encoding";
	
	public static final String VARY = "Vary";
	
	public static final String CONTENT_ENCODING = "Content-Encoding";
	
    /**
     * 返回指定的请求头信息
     * @param name 变量名
     * @return 信息
     */
    public static String getHeader(HttpServletRequest request, String name) {
        return request.getHeader(name);
    }
    
    /**
     * 返回上一个页面的地址
     * @return 上一个页面地址
     */
    public static String getUrlReferer(HttpServletRequest request) {
    	return getHeader(request, REFERER);
    }

    /**
     * 返回指定的请求头信息
     * @param name 变量名
     * @return 信息
     */
    @SuppressWarnings("unchecked")
	public static Iterator<String> getHeaders(HttpServletRequest request, String name) {
    	Enumeration<String> enumeration = request.getHeaders(name);
    	return new EnumerationIterator(enumeration);
    }
    
    /**
     * 返回请求可接口的编码方式。
     * @return 请求可接口的编码方式。
     */
    public static Iterator<String> getAcceptEncoding(HttpServletRequest request) {
    	return getHeaders(request, ACCEPT_ENCODING);
    }
    
    /**
     * 添加信息到响应头。
     * @param name 变量名。
     * @param value 信息。
     */
    public static void addHeader(HttpServletResponse response, String name, String value) {
    	String header = response.getHeader(name);
    	if (!StringUtils.contains(header, value)) {
    		response.addHeader(name, value);
    	}
    }
    
    /**
     * 设置信息到响应头。
     * @param name 变量名。
     * @param value 信息。
     */
    public static void setHeader(HttpServletResponse response, String name, String value) {
    	String header = response.getHeader(name);
    	if (!StringUtils.contains(header, value)) {
    		response.setHeader(name, value);
    	}
    }
	
	/**
	 * 获取请求URL。
	 * @param request 当前请求。
	 * @return 请求URL。
	 */
	public static String getRequestURL(HttpServletRequest request) {
		StringBuilder requestURIWithParam = new StringBuilder();
		requestURIWithParam.append(request.getRequestURI());
		String requestQuery = request.getQueryString();
		if (StringUtils.isNotEmpty(requestQuery)) {
			requestURIWithParam.append("?").append(requestQuery);
		}
		return requestURIWithParam.toString();
	}
	
	/**
	 * 获取客户端IP。
	 * @param request 当前请求。
	 * @return 客户端IP。
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !StringUtils.equalsIgnoreCase("unknow", ip)) {
			return ip;
		}
		ip = request.getHeader("X-forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !StringUtils.equalsIgnoreCase("unknow", ip)) {
			return StringUtils.substringBefore(ip, ",");
		} else {
			return request.getRemoteAddr();
		}
	}
}
