package org.danielli.xultimate.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.danielli.xultimate.web.util.VisitLimiter;
import org.springframework.web.filter.GenericFilterBean;

/**
 * 浏览限制拦截器。
 *
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public abstract class VisitLimiterFilter extends GenericFilterBean {

	protected int expMillionSeconds;
	
	protected int visitLimiterCount;
	
	private VisitLimiter visitLimiter;

	public void setExpMillionSeconds(int expMillionSeconds) {
		this.expMillionSeconds = expMillionSeconds;
	}

	public void setVisitLimiterCount(int visitLimiterCount) {
		this.visitLimiterCount = visitLimiterCount;
	}

	public void setVisitLimiter(VisitLimiter visitLimiter) {
		this.visitLimiter = visitLimiter;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String key = getKey(request, response);
		boolean allowBrowse = visitLimiter.allowBrowse(key, expMillionSeconds, visitLimiterCount);
		if (allowBrowse) {
			chain.doFilter(request, response);
		} else {
			doRestrictions(request, response, chain);
		}
	}
	
	public abstract String getKey(ServletRequest request, ServletResponse response);

	public abstract void doRestrictions(ServletRequest request, ServletResponse response, FilterChain chain);
}
