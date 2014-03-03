package org.danielli.xultimate.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.danielli.xultimate.context.kvStore.memcached.xmemcached.MemcachedClientLimiter;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 浏览访问限制过滤器。
 *
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public abstract class BrowseLimiterFilter extends OncePerRequestFilter {

	/** 缓存时间 */
	protected int cacheSeconds;
	/** 访问次数限制 */
	protected int visitLimiterCount;
	/** 访问限制器 */
	protected MemcachedClientLimiter memcachedClientLimiter;

	/**
	 * 设置缓存时间。
	 * 
	 * @param cacheSeconds 缓存时间。
	 */
	public void setCacheSeconds(int cacheSeconds) {
		this.cacheSeconds = cacheSeconds;
	}
	
	/***
	 * 设置访问次数限制。
	 * 
	 * @param visitLimiterCount 访问次数限制。
	 */
	public void setVisitLimiterCount(int visitLimiterCount) {
		this.visitLimiterCount = visitLimiterCount;
	}

	/**
	 * 设置访问限制器。
	 * 
	 * @param memcachedClientLimiter 访问限制器。
	 */
	public void setMemcachedClientLimiter(MemcachedClientLimiter memcachedClientLimiter) {
		this.memcachedClientLimiter = memcachedClientLimiter;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String key = getKey(request, response);
		boolean allowBrowse = memcachedClientLimiter.allowBrowse(key, cacheSeconds, visitLimiterCount);
		if (allowBrowse) {
			filterChain.doFilter(request, response);
		} else {
			doRestrictions(request, response, filterChain);
		}
	}
	
	/**
	 * 获取缓存KEY。
	 * 
	 * @param request 请求对象。
	 * @param response 响应对象。
	 * @return 缓存KEY。
	 */
	public abstract String getKey(ServletRequest request, ServletResponse response);

	/**
	 * 若是不允许访问，应该执行的操作。
	 * 
	 * @param request 请求对象。
	 * @param response 响应对象。
	 * @param chain 执行链。
	 */
	public abstract void doRestrictions(ServletRequest request, ServletResponse response, FilterChain chain);


}
