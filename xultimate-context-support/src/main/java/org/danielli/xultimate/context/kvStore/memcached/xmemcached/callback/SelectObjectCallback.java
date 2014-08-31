package org.danielli.xultimate.context.kvStore.memcached.xmemcached.callback;

import org.danielli.xultimate.context.format.FormatterUtils;
import org.danielli.xultimate.context.kvStore.memcached.MemcachedException;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedClient;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.MemcachedLockFactory.MemcachedLock;

/**
 * 用于单个对象的通用缓存处理。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 *
 * @param <T> 回调返回值。
 */
public abstract class SelectObjectCallback<T> implements XMemcachedClientCallback<T> {

	@Override
	public T doInXMemcached(XMemcachedClient xMemcachedClient) throws Exception {
		String cachedKey = getCachedKey();
		CallbackLockConfig config = getCallbackLockConfig();
		if (config == null) {
			return doInXMemcached0(xMemcachedClient, cachedKey, null, 0, 0, 0);
		} else {
			return doInXMemcached0(xMemcachedClient, cachedKey, config.getMemcachedLock(), 0, config.getRetryFrequency(), config.getIntervalPeriodMillisecond());
		}
	}
	
	public T doInXMemcached0(XMemcachedClient xMemcachedClient, String cachedKey, MemcachedLock memcachedLock, int currentRetryFrequency, int retryFrequency, int intervalPeriodMillisecond) throws Exception {
		if (currentRetryFrequency == retryFrequency) {
			return null;
		}
		
		T cachedObject = xMemcachedClient.get(cachedKey);
		if (cachedObject == null) {
			if (memcachedLock == null) {
				return getCachedObject(xMemcachedClient, cachedKey);
			} else {
				String cachedKeyLock = FormatterUtils.format("{0}.lock", cachedKey);
				try {
					if (memcachedLock.tryLock(cachedKeyLock)) {
						try {
							return getCachedObject(xMemcachedClient, cachedKey);
						} finally {
							memcachedLock.unlock(cachedKeyLock);
						}
					} else {
						Thread.sleep(intervalPeriodMillisecond);
						return doInXMemcached0(xMemcachedClient, cachedKey, memcachedLock, currentRetryFrequency++, retryFrequency, intervalPeriodMillisecond);
					}
				} catch (MemcachedException e) {
					return doInXMemcached0(xMemcachedClient, cachedKey, null, 0, 0, 0);
				}
			}
		}
		return cachedObject;
	}
	
	private T getCachedObject(XMemcachedClient xMemcachedClient, String cachedKey) {
		T cachedObject = doGetCachedObject();
		if (cachedObject != null) {
			xMemcachedClient.set(cachedKey, getExpireSeconds(), cachedObject);
		}
		return cachedObject;
	}
	
	/**
	 * 获取缓存KEY。
	 * @return 缓存KEY。
	 */
	public abstract String getCachedKey();
	
	/**
	 * 获取缓存对象。
	 * @return 缓存对象。
	 */
	public abstract T doGetCachedObject();
	
	/**
	 * 获取缓存失效时间。
	 * @return 缓存失效时间。
	 */
	public abstract int getExpireSeconds();

	/**
	 * 获取锁配置。
	 * @return 锁配置。
	 */
	public abstract CallbackLockConfig getCallbackLockConfig();

}
