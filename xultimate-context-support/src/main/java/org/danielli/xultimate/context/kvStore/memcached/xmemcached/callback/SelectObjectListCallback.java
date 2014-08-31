package org.danielli.xultimate.context.kvStore.memcached.xmemcached.callback;

import java.util.ArrayList;
import java.util.List;

import org.danielli.xultimate.context.format.FormatterUtils;
import org.danielli.xultimate.context.kvStore.memcached.MemcachedException;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedClient;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.MemcachedLockFactory.MemcachedLock;

public abstract class SelectObjectListCallback<T, M extends List<T>> implements XMemcachedClientCallback<M> {

	@Override
	public M doInXMemcached(XMemcachedClient xMemcachedClient) throws Exception {
		String cachedObjectKeyListKey = getCachedObjectKeyListKey();
		CallbackLockConfig config = getCallbackLockConfig();
		if (config == null) {
			return doInXMemcached0(xMemcachedClient, cachedObjectKeyListKey, null, 0, 0, 0);
		} else {
			return doInXMemcached0(xMemcachedClient, cachedObjectKeyListKey, config.getMemcachedLock(), 0, config.getRetryFrequency(), config.getIntervalPeriodMillisecond());
		}
	}
	
	public M doInXMemcached0(XMemcachedClient xMemcachedClient, String cachedObjectKeyListKey, MemcachedLock memcachedLock, int currentRetryFrequency, int retryFrequency, int intervalPeriodMillisecond) throws Exception {
		if (currentRetryFrequency == retryFrequency) {
			return null;
		}
		
		List<String> cachedObjectKeyList = xMemcachedClient.get(cachedObjectKeyListKey);
		if (cachedObjectKeyList == null) {
			if (memcachedLock == null) {
				return getCachedObjectList(xMemcachedClient, cachedObjectKeyListKey);
			} else {
				String cachedObjectKeyListKeyLock = FormatterUtils.format("{0}.lock", cachedObjectKeyListKey);
				try {
					if (memcachedLock.tryLock(cachedObjectKeyListKeyLock)) {
						try {
							return getCachedObjectList(xMemcachedClient, cachedObjectKeyListKeyLock);
						} finally {
							memcachedLock.unlock(cachedObjectKeyListKeyLock);
						}
					} else {
						Thread.sleep(intervalPeriodMillisecond);
						return doInXMemcached0(xMemcachedClient, cachedObjectKeyListKey, memcachedLock, currentRetryFrequency++, retryFrequency, intervalPeriodMillisecond);
					}
				} catch (MemcachedException e) {
					return doInXMemcached0(xMemcachedClient, cachedObjectKeyListKey, null, 0, 0, 0);
				}
			}
		} else {
			M cachedObjectList = newList(cachedObjectKeyList.size());
			for (String cachedObjectKey : cachedObjectKeyList) {
				T cachedObject = xMemcachedClient.get(cachedObjectKey);
				if (cachedObject != null)
					cachedObjectList.add(cachedObject);
				else
					break;
			}
			if (cachedObjectList.size() == cachedObjectKeyList.size()) {
				return cachedObjectList; 
			} else {
				return doInXMemcached0(xMemcachedClient, cachedObjectKeyListKey, memcachedLock, currentRetryFrequency++, retryFrequency, intervalPeriodMillisecond);
			}
		}
	}
	
	/**
	 * 获取缓存对象。
	 * @return 缓存对象。
	 */
	public abstract M doGetCachedObjectList();
	
	private M getCachedObjectList(XMemcachedClient xMemcachedClient, String cachedObjectKeyListKey) {
		M cachedObjectList = doGetCachedObjectList();
		if (cachedObjectList != null) {
			M newCachedObjectList = newList(cachedObjectList.size());
			List<String> cachedObjectKeyList = new ArrayList<>(cachedObjectList.size());
			for (T cachedObject : cachedObjectList) {
				newCachedObjectList.add(cachedObject);
				String cachedObjectKey = getCachedObjectKey(cachedObject);
				cachedObjectKeyList.add(cachedObjectKey);
				xMemcachedClient.set(cachedObjectKey, getExpireSeconds(), cachedObject);
			}
			xMemcachedClient.set(cachedObjectKeyListKey, getExpireSeconds(), cachedObjectKeyList);
			return newCachedObjectList;
		} else {
			return newList(0);
		}
	}
	
	/**
	 * 获取缓存对象KEY集合的KEY。
	 * @return 缓存对象KEY集合的KEY。
	 */
	public abstract String getCachedObjectKeyListKey();
	
	/**
	 * 获取新集合。
	 * 
	 * @param cachedObjectKeyListSize 缓存对象KEY集合大小。
	 * @return 新的集合。
	 */
	public abstract M newList(int cachedObjectKeyListSize);
	
	/**
	 * 获取缓存对象KEY。
	 * @param cachedObject 缓存对象。
	 * @return 缓存对象KEY。
	 */
	public abstract String getCachedObjectKey(T cachedObject);
	
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
