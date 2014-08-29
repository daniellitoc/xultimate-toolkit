package org.danielli.xultimate.context.kvStore.redis.jedis.util;

import org.danielli.xultimate.context.kvStore.redis.jedis.ShardedJedisCallback;
import org.danielli.xultimate.context.kvStore.redis.jedis.ShardedJedisCallback;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * ShardedJedisPool工具类。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 */
public class ShardedJedisPoolUtils {
	
	/**
	 * 执行回调。
	 * 
	 * @param shardedJedisPool Jedis客户端。
	 * @param shardedJedisReturnedCallback 回调。
	 * @return 回调返回值。
	 * @exception Exception 任何可能出现的异常。
	 */
	public static <T> T execute(ShardedJedisPool shardedJedisPool, ShardedJedisCallback<T> shardedJedisReturnedCallback) throws Exception {
		ShardedJedis shardedJedis = null;
	    try {   
	    	shardedJedis = shardedJedisPool.getResource();
	        return shardedJedisReturnedCallback.doInShardedJedis(shardedJedis);
	    } finally{
	    	if (shardedJedis != null) {
	    		shardedJedisPool.returnResource(shardedJedis);
	    	}
	    }
	}
}
