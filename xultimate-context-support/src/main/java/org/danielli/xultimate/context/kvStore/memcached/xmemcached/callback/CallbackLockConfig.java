package org.danielli.xultimate.context.kvStore.memcached.xmemcached.callback;

import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.MemcachedLockFactory.MemcachedLock;

/**
 * Callback锁配置。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 */
public class CallbackLockConfig {
	/** Memcached锁 */
	private MemcachedLock memcachedLock;
	/** 重试频率 */
	private int retryFrequency;
	/** 间隔周期毫秒 */
	private int intervalPeriodMillisecond;
	
	public CallbackLockConfig(MemcachedLock memcachedLock, int retryFrequency, int intervalPeriodMillisecond) {
		this.memcachedLock = memcachedLock;
		this.retryFrequency = retryFrequency;
		this.intervalPeriodMillisecond = intervalPeriodMillisecond;
	}
	
	public MemcachedLock getMemcachedLock() {
		return memcachedLock;
	}
	
	public int getRetryFrequency() {
		return retryFrequency;
	}
	
	public int getIntervalPeriodMillisecond() {
		return intervalPeriodMillisecond;
	}
	
}
