package org.danielli.xultimate.context.kvStore.redis.jedis.support;

import org.danielli.xultimate.context.kvStore.redis.AbstractRedisTemplate;
import org.danielli.xultimate.context.kvStore.redis.RedisException;
import org.danielli.xultimate.context.kvStore.redis.jedis.ShardedJedisCallback;
import org.danielli.xultimate.context.kvStore.redis.jedis.util.ShardedJedisPoolUtils;

import redis.clients.jedis.ShardedJedisPool;

/**
 * Jedis模板类。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 * @see AbstractRedisTemplate
 */
public class ShardedJedisTemplate extends AbstractRedisTemplate {

	/** Jedis客户端 */
	protected ShardedJedisPool shardedJedisPool;

	/**
	 * 设置Jedis客户端
	 * @param shardedJedisPool Jedis客户端
	 */
	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}
	
	/**
	 * 此方法可以通过Jedis客户端执行相应功能。
	 * 
	 * @param shardedJedisReturnedCallback 回调。
	 * @return 回调指定值。
	 */
	public <T> T execute(ShardedJedisCallback<T> shardedJedisReturnedCallback) throws RedisException {
		try {
			return ShardedJedisPoolUtils.execute(shardedJedisPool, shardedJedisReturnedCallback);
		} catch (Exception e) {
			handleException(wrapperException(e));
			return null;		// TODO 补全
		}
	}
}
