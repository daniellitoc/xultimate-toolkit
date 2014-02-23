package org.danielli.xultimate.context.kvStore.redis;

import org.danielli.xultimate.context.kvStore.AbstractKeyValueStoreTemplate;
import org.danielli.xultimate.context.kvStore.KeyValueStoreException;

/**
 * 抽象Redis模板类。主要提供异常记录功能。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 */
public abstract class AbstractRedisTemplate extends AbstractKeyValueStoreTemplate {
	
	/**
	 * 抛出异常。
	 */
	@Override
	protected void handleException(KeyValueStoreException e) throws RedisException {
		throw e;
	}
	
	@Override
	protected RedisException wrapperException(Exception e) {
		if (e instanceof RedisException) {
			return (RedisException) e;
		}
		return new RedisException(e.getMessage(), e);
	}
}
