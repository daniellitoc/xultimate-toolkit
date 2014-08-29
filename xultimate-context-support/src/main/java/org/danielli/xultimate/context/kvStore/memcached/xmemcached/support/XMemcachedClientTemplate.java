package org.danielli.xultimate.context.kvStore.memcached.xmemcached.support;

import org.danielli.xultimate.context.kvStore.memcached.AbstractMemcachedTemplate;
import org.danielli.xultimate.context.kvStore.memcached.MemcachedException;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedClient;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedClientCallback;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.util.XMemcachedClientUtils;

/**
 * XMemcached模板类。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 * @see AbstractMemcachedTemplate
 */
public class XMemcachedClientTemplate extends AbstractMemcachedTemplate {

	/** XMemcached客户端 */
	protected XMemcachedClient xMemcachedClient;
	
	/**
	 * 设置XMemcached客户端
	 * @param xMemcachedClient XMemcached客户端
	 */
	public void setxMemcachedClient(XMemcachedClient xMemcachedClient) {
		this.xMemcachedClient = xMemcachedClient;
	}
	
	/**
	 * 此方法可以通过XMemcached客户端执行相应功能。
	 * 
	 * @param xMemcachedClientCallback 回调。
	 * @return 回调指定值。
	 */
	public <T> T execute(XMemcachedClientCallback<T> xMemcachedClientCallback) throws MemcachedException {
		try {
			return XMemcachedClientUtils.execute(xMemcachedClient, xMemcachedClientCallback);
		} catch (Exception e) {
			handleException(wrapperException(e));
			return null;
		}
	}
	
	/**
	 * With the namespae to do something with current memcached client.All
	 * operations with memcached client done in callable will be under the
	 * namespace. {@link #beginWithNamespace(String)} and {@link #endWithNamespace()} will called around automatically.
	 * For example:
	 * <pre>
	 *   memcachedClient.withNamespace(userId,new XMemcachedClientCallback<Void>{
	 *     public Void doInXMemcached(XMemcachedClient client) throws Exception {
	 *      client.set("username",0,username);
	 *      client.set("email",0,email);
	 *      return null;
	 *     }
	 *   });
	 *   //invalidate all items under the namespace.
	 *   memcachedClient.invalidateNamespace(userId);
	 * </pre>
	 * 
	 * @since 1.4.2
	 * @param namespace 命名空间
	 * @param xMemcachedClientCallback 回调。
	 * @see #beginWithNamespace(String)
	 * @see #endWithNamespace()
	 * @return 回调返回值。
	 * @exception Exception 任何可能出现的异常。
	 */
	public <T> T withNamespace(String namespace, XMemcachedClientCallback<T> xMemcachedClientCallback) throws MemcachedException {
		try {
			return XMemcachedClientUtils.withNamespace(namespace, xMemcachedClient, xMemcachedClientCallback);
		} catch (Exception e) {
			handleException(wrapperException(e));
			return null;
		}
	}
	
}
