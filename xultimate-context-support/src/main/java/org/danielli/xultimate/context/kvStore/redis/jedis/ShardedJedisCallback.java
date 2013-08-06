package org.danielli.xultimate.context.kvStore.redis.jedis;

import redis.clients.jedis.ShardedJedis;

/**
 * ShardedJedis回调。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 */
public interface ShardedJedisCallback {

	/**
	 * 回调实现。
	 * 
	 * @param shardedJedis Jedis客户端。
	 */
	void doInShardedJedis(ShardedJedis shardedJedis);
}
