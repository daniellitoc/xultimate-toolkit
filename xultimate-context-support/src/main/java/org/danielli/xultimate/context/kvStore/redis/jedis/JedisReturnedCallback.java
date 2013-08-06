package org.danielli.xultimate.context.kvStore.redis.jedis;

import redis.clients.jedis.Jedis;

/**
 * Jedis回调。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 *
 * @param <T> 回调返回值。
 */
public interface JedisReturnedCallback<T> {

	/**
	 * 回调实现。
	 * 
	 * @param jedis Jedis客户端。
	 * @return 回调返回值。
	 */
	T doInJedis(Jedis jedis);
}
