package org.danielli.xultimate.context.kvStore.memcached.xmemcached;


/**
 * XMemcached回调。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 *
 * @param <T> 回调返回值。
 */
public interface XMemcachedClientCallback<T> {
	
	/**
	 * 回调实现。
	 * 
	 * @param xMemcachedClient XMemcached客户端。
	 * @return 回调返回值。
	 * @exception Exception 任何可能出现的异常。
	 */
	T doInXMemcached(XMemcachedClient xMemcachedClient) throws Exception;
}
