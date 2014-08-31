package org.danielli.xultimate.context.kvStore.memcached.xmemcached.support;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.rubyeye.xmemcached.XMemcachedClient;

import org.danielli.xultimate.context.kvStore.memcached.MemcachedException;
import org.danielli.xultimate.util.Assert;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分布式锁工厂。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
public class MemcachedLockFactory {
	/** XMemcached客户端 */
	protected XMemcachedClient xMemcachedClient;
	
	private ConcurrentMap<Object, MemcachedLock> lockMap = new ConcurrentHashMap<>();
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MemcachedLockFactory.class);
	
	/**
	 * 设置XMemcached客户端。
	 * @param xMemcachedClient XMemcached客户端。
	 */
	public void setxMemcachedClient(XMemcachedClient xMemcachedClient) {
		this.xMemcachedClient = xMemcachedClient;
	}
	
	/**
	 * 获取锁。
	 * @param expireSeconds 失效时间。
	 * @return 锁实例。
	 */
	public MemcachedLock getLock(int expireSeconds) {
		MemcachedLock memcachedLock = lockMap.get(expireSeconds);
		if (memcachedLock == null) {
			synchronized (this) {
				memcachedLock = lockMap.get(lockMap);
				if (memcachedLock == null) {
					memcachedLock = new ExpireSecondsMemcachedLock(expireSeconds);
					lockMap.put(expireSeconds, memcachedLock);
				}
			}
		}
		return memcachedLock;
	}
	
	/**
	 * 获取锁。
	 * @param expireSeconds 失效日期。
	 * @return 锁实例。
	 */
	public MemcachedLock getLock(Date expireDate) {
		MemcachedLock memcachedLock = lockMap.get(expireDate);
		if (memcachedLock == null) {
			synchronized (this) {
				memcachedLock = lockMap.get(lockMap);
				if (memcachedLock == null) {
					memcachedLock = new ExpireDateMemcachedLock(new DateTime(expireDate));
					lockMap.put(expireDate, memcachedLock);
				}
			}
		}
		return memcachedLock;
	}
	
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
	public interface MemcachedLock {
		/**
		 * 锁。
		 * @param lockName 锁名称。
		 * @return 是否获取锁，如果为true表示获取成功，否则表示获取失败。
		 * @exception MemcachedException 可能出现的任何异常。
		 */
		boolean tryLock(String lockName) throws MemcachedException;
		
		/**
		 * 解锁。
		 * @param lockName 锁名称。
		 * @exception MemcachedException 可能出现的任何异常。
		 */
		void unlock(String lockName) throws MemcachedException;
	}
	
	/**
	 * 抽象锁。
	 * 
	 * @author Daniel Li
	 * @since 15 Jun 2013
	 */
	abstract class AbstractMemcachedLock implements MemcachedLock {
		
		@Override
		public boolean tryLock(String lockName) throws MemcachedException {
			Assert.notNull(lockName, "this argument `lockName` is required; it must not be null");
			try {
				return xMemcachedClient.add(lockName, getExpireSeconds(), true);
			} catch (Exception e) {
				LOGGER.error("Use MemcachedLock caught Exception");
				LOGGER.error(e.getMessage(), e);
				throw new MemcachedException(e.getMessage(), e);
			}
		}
		
		@Override
		public void unlock(String lockName) throws MemcachedException {
			Assert.notNull(lockName, "this argument `lockName` is required; it must not be null");
			try {
				xMemcachedClient.delete(lockName);
			} catch (Exception e) {
				LOGGER.error("Use MemcachedLock caught Exception");
				LOGGER.error(e.getMessage(), e);
				throw new MemcachedException(e.getMessage(), e);
			}
		}
		
		protected abstract int getExpireSeconds();
	}
	
	/**
	 * 失效时间锁。
	 * 
	 * @author Daniel Li
	 * @since 15 Jun 2013
	 */
	class ExpireSecondsMemcachedLock extends AbstractMemcachedLock {
		
		private int expSeconds;
		
		public ExpireSecondsMemcachedLock(int expSeconds) {
			this.expSeconds = expSeconds;
		}
		
		@Override
		protected int getExpireSeconds() {
			return expSeconds;
		}
	}
	
	/**
	 * 失效日期锁。
	 * 
	 * @author Daniel Li
	 * @since 15 Jun 2013
	 */
	class ExpireDateMemcachedLock extends AbstractMemcachedLock {
		/** 失效指定日期 */
		private DateTime expireDate;
		
		public ExpireDateMemcachedLock(DateTime expireDate) {
			this.expireDate = expireDate;
		}
		
		/**
		 * 转换时间。
		 * @return 失效时间。
		 */
		protected int getExpireSeconds() {
			Duration duration = new Duration(DateTime.now(), expireDate);
			if (duration.getStandardSeconds() <= 0) {
				LOGGER.warn("expireDate must be great than now, ignore expireDate");
				return 0;
			} else {
				return (int) duration.getStandardSeconds();
			}
		}
	}
	
}
