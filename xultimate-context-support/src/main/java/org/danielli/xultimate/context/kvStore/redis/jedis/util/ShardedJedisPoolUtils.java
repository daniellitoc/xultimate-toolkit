package org.danielli.xultimate.context.kvStore.redis.jedis.util;

import org.danielli.xultimate.context.kvStore.redis.jedis.ShardedJedisCallback;
import org.danielli.xultimate.context.kvStore.redis.jedis.ShardedJedisReturnedCallback;

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
	public static <T> T execute(ShardedJedisPool shardedJedisPool, ShardedJedisReturnedCallback<T> shardedJedisReturnedCallback) throws Exception {
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
	
	/**
	 * 执行回调。
	 * 
	 * @param shardedJedisPool Jedis客户端。
	 * @param shardedJedisCallback 回调。
	 * @exception Exception 任何可能出现的异常。
	 */
	public static void execute(ShardedJedisPool shardedJedisPool, ShardedJedisCallback shardedJedisCallback) throws Exception {
		ShardedJedis shardedJedis = null;
	    try {   
	    	shardedJedis = shardedJedisPool.getResource();
	        shardedJedisCallback.doInShardedJedis(shardedJedis);
	    } finally{
	    	if (shardedJedis != null) {
	    		shardedJedisPool.returnResource(shardedJedis);
	    	}
	    }
	}
}
