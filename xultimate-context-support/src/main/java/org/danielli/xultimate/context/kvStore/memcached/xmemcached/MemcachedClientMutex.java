package org.danielli.xultimate.context.kvStore.memcached.xmemcached;

import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.XMemcachedClient;
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

	private int expMillionSeconds;

	public void setExpMillionSeconds(int expMillionSeconds) {
		this.expMillionSeconds = expMillionSeconds;
	}

	public boolean tryLock(XMemcachedClient memcachedClient, String lockKey)
			throws TimeoutException, InterruptedException, MemcachedException {
		return memcachedClient.add(lockKey, expMillionSeconds, true);
	}

	public void unlock(XMemcachedClient memcachedClient, String lockKey)
			throws TimeoutException, InterruptedException, MemcachedException {
		memcachedClient.delete(lockKey);
	}
}
