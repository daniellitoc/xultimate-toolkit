package org.danielli.xultimate.context.kvStore.redis.jedis;

import redis.clients.jedis.Jedis;

/**
 * Jedis回调。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 */
public interface JedisCallback {
	
	/**
	 * 回调实现。
	 * 
	 * @param jedis Jedis客户端。
	 * @exception Exception 任何可能出现的异常。
	 */
	void doInJedis(Jedis jedis) throws Exception;
}
