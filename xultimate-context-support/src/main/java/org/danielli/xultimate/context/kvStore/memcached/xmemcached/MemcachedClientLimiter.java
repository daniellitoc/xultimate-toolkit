package org.danielli.xultimate.context.kvStore.memcached.xmemcached;

import net.rubyeye.xmemcached.MemcachedClient;

import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.XMemcachedTemplate;

/**
 * 访问限制器。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public class MemcachedClientLimiter {

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
	
	/**
	 * 是否可以继续访问。
	 * 
	 * @param key 健。
	 * @param expSeconds Key过期秒数。
	 * @param visitLimiterCount 限制的访问个数。
	 * @return 如果返回true，表示可以继续访问；否则返回false。
	 */
	public boolean allowBrowse(final String key, final int expSeconds, final int visitLimiterCount) {  
	    Boolean result = xMemcachedTemplate.execute(new XMemcachedReturnedCallback<Boolean>() {

			@Override
			public Boolean doInXMemcached(MemcachedClient memcachedClient) throws Exception {
				long currentBrowseCount = memcachedClient.incr(key, visitStep, initialVisitCount, memcachedClient.getOpTimeout(), expSeconds);
				return currentBrowseCount > visitLimiterCount ? false : true;
			}
			
		});
	    return result == null ? true : result;
	}
}
