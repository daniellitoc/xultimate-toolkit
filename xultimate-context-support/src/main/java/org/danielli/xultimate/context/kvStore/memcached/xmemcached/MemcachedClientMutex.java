package org.danielli.xultimate.context.kvStore.memcached.xmemcached;

import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

/**
 * Memcached互斥锁，用于Callback内部。如下参考原型(http://timyang.net/programming/memcache-mutex/)。
 * <pre>
 * 	if (memcache.get(key) == null) {
 * 		// 3 min timeout to avoid mutex holder crash
 * 		if (memcache.add(key_mutex, 3 * 60 * 1000) == true) {
 * 			value = db.get(key);
 * 			memcache.set(key, value);
 * 			memcache.delete(key_mutex);
 * 		} else {
 * 			sleep(50);
 * 			retry();
 * 		}
 * 	}
 * </pre>
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 */
public class MemcachedClientMutex {

	/** 失效时间 */
	private int expSeconds;

	/**
	 * 设置失效时间。
	 * 
	 * @param expSeconds 失效时间。
	 */
	public void setExpSeconds(int expSeconds) {
		this.expSeconds = expSeconds;
	}

	/**
	 * 设置锁。
	 * 
	 * @param memcachedClient Memcached客户端。
	 * @param lockKey 锁名称。
	 * @return 如果返回true，则表示成功获取锁，否则表示锁正在被占用。
	 * @throws TimeoutException 
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public boolean tryLock(MemcachedClient memcachedClient, String lockKey)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.add(lockKey, expSeconds, true);
	}

	/**
	 * 解锁。
	 * 
	 * @param memcachedClient Memcached客户端。
	 * @param lockKey 解锁名称。
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public void unlock(MemcachedClient memcachedClient, String lockKey)
			throws TimeoutException, InterruptedException, MemcachedException {
		memcachedClient.delete(lockKey);
	}
}
