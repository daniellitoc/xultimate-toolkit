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
	
	public static <T> T execute(ShardedJedisPool shardedJedisPool, ShardedJedisReturnedCallback<T> shardedJedisReturnedCallback) {
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
	
	public static void execute(ShardedJedisPool shardedJedisPool, ShardedJedisCallback shardedJedisCallback) {
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
