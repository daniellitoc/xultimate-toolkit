package org.danielli.xultimate.web.util;

import net.rubyeye.xmemcached.MemcachedClient;

import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedReturnedCallback;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.XMemcachedTemplate;

/**
 * 浏览限制器。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public class VisitLimiter {
	/** 每次增长的步进 */
	private int visitStep = 1;
	
	/** 初始访问个数 */
	private int initialVisitCount = 0;
	
	/** XMemcached模板类 */
	protected XMemcachedTemplate xMemcachedTemplate;
	
	public void setVisitStep(int visitStep) {
		this.visitStep = visitStep;
	}

	public void setInitialVisitCount(int initialVisitCount) {
		this.initialVisitCount = initialVisitCount;
	}
	
	public void setxMemcachedTemplate(XMemcachedTemplate xMemcachedTemplate) {
		this.xMemcachedTemplate = xMemcachedTemplate;
	}
	
	public boolean allowBrowse(final String key, final int expMillionSeconds, final int visitLimiterCount) {  
	    Boolean result = xMemcachedTemplate.execute(new XMemcachedReturnedCallback<Boolean>() {

			@Override
			public Boolean doInXMemcached(MemcachedClient memcachedClient) throws Exception {
				long currentBrowseCount = memcachedClient.incr(key, visitStep, initialVisitCount, memcachedClient.getOpTimeout(), expMillionSeconds);
				return currentBrowseCount > visitLimiterCount ? false : true;
			}
			
		});
	    return result == null ? true : result;
	}
}
