package org.danielli.xultimate.context.kvStore.memcached.xmemcached.util;

import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedClient;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.callback.XMemcachedClientCallback;

public class XMemcachedClientUtils {

	/**
	 * 执行回调。
	 * 
	 * @param xMemcachedClient XMemcached客户端。
	 * @param xMemcachedClientCallback 回调。
	 * @return 回调返回值。
	 * @exception Exception 任何可能出现的异常。
	 */
	public static <T> T execute(XMemcachedClient xMemcachedClient, XMemcachedClientCallback<T> xMemcachedClientCallback) throws Exception {
		return xMemcachedClientCallback.doInXMemcached(xMemcachedClient);
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
	 * @param xMemcachedClient XMemcached客户端。
	 * @param xMemcachedClientCallback 回调。
	 * @see #beginWithNamespace(String)
	 * @see #endWithNamespace()
	 * @return 回调返回值。
	 * @exception Exception 任何可能出现的异常。
	 */
	public static <T> T withNamespace(String namespace, XMemcachedClient xMemcachedClient, XMemcachedClientCallback<T> xMemcachedClientCallback) throws Exception {
		xMemcachedClient.beginWithNamespace(namespace);
		try {
			return xMemcachedClientCallback.doInXMemcached(xMemcachedClient);
		} finally {
			xMemcachedClient.endWithNamespace();
		}
	}
}
