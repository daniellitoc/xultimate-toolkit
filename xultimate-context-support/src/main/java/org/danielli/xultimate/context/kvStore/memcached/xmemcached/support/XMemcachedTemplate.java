package org.danielli.xultimate.context.kvStore.memcached.xmemcached.support;

import net.rubyeye.xmemcached.MemcachedClient;

import org.danielli.xultimate.context.kvStore.memcached.AbstractMemcachedTemplate;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedCallback;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedReturnedCallback;

/**
 * XMemcached模板类。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 * @see AbstractMemcachedTemplate
 */
public class XMemcachedTemplate extends AbstractMemcachedTemplate {
	
	/** XMemcached客户端 */
	protected MemcachedClient memcachedClient;
	
	/**
	 * 此方法可以通过XMemcached客户端执行相应功能。
	 * 
	 * @param callback 回调。
	 * @return 回调指定值。
	 */
	public <T> T execute(XMemcachedReturnedCallback<T> callback) {
		try {
			return callback.doInXMemcached(memcachedClient);
		} catch (Exception e) {
			handleException(wrapperException(e));
			return null;
		}
	}
	
	/**
	 * 此方法可以通过XMemcached客户端执行相应功能。
	 * 
	 * @param callback 回调。
	 */
	public void execute(XMemcachedCallback callback) {
		try {
			callback.doInXMemcached(memcachedClient);
		} catch (Exception e) {
			handleException(wrapperException(e));
		}
	}
	
	/**
	 * 设置XMemcached客户端
	 * @param memcachedClient XMemcached客户端
	 */
	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}
}
