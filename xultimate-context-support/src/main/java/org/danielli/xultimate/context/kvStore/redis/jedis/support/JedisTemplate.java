package org.danielli.xultimate.context.kvStore.redis.jedis.support;

import org.danielli.xultimate.context.kvStore.redis.AbstractRedisTemplate;
import org.danielli.xultimate.context.kvStore.redis.RedisException;
import org.danielli.xultimate.context.kvStore.redis.jedis.JedisCallback;
import org.danielli.xultimate.context.kvStore.redis.jedis.util.JedisPoolUtils;

import redis.clients.jedis.JedisPool;

/**
 * Jedis模板类。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 * @see AbstractRedisTemplate
 */
public class JedisTemplate extends AbstractRedisTemplate {

	/** Jedis客户端 */
	protected JedisPool jedisPool;

	/**
	 * 设置Jedis客户端
	 * @param jedisPool Jedis客户端
	 */
	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
	
	/**
	 * 此方法可以通过Jedis客户端执行相应功能。
	 * 
	 * @param jedisReturnedCallback 回调。
	 * @return 回调指定值。
	 */
	public <T> T execute(JedisCallback<T> jedisReturnedCallback) throws RedisException {
		try {
			return JedisPoolUtils.execute(jedisPool, jedisReturnedCallback);
		} catch (Exception e) {
			handleException(wrapperException(e));
			return null;		// TODO 补全
		}
	}
}
