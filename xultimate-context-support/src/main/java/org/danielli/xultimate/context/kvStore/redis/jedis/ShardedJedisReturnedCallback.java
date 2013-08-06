package org.danielli.xultimate.context.kvStore.redis.jedis;

import redis.clients.jedis.ShardedJedis;

/**
 * ShardedJedis回调。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 *
 * @param <T> 回调返回值。
 */
public interface ShardedJedisReturnedCallback<T> {

	/**
	 * 回调实现。
	 * 
	 * @param shardedJedis Jedis客户端。
	 * @return 回调返回值。
	 */
	T doInShardedJedis(ShardedJedis shardedJedis);
}
