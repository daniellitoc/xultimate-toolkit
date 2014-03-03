package org.danielli.xultimate.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.danielli.xultimate.web.util.BrowserCacheGenerator;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 浏览器缓存过滤器。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public class BrowserCacheFilter extends OncePerRequestFilter {

	/** 浏览器缓存生成器 */
	protected BrowserCacheGenerator browserCacheGenerator;
	/** 缓存时间 */
	protected Integer cacheSeconds;
	
	/**
	 * 设置浏览器缓存生成器。
	 * 
	 * @param browserCacheGenerator 浏览器缓存生成器。
	 */
	public void setBrowserCacheGenerator(BrowserCacheGenerator browserCacheGenerator) {
		this.browserCacheGenerator = browserCacheGenerator;
	}
	
	/**
	 * 设置缓存时间。
	 * 
	 * @param cacheSeconds 缓存时间。
	 */
	public void setCacheSeconds(Integer cacheSeconds) {
		this.cacheSeconds = cacheSeconds;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		filterChain.doFilter(request, response);
		browserCacheGenerator.checkAndPrepare(request, response, cacheSeconds, true);
	}

}
