package org.danielli.xultimate.context.kvStore.redis.jedis.util;

import org.danielli.xultimate.context.kvStore.redis.jedis.JedisCallback;
import org.danielli.xultimate.context.kvStore.redis.jedis.JedisReturnedCallback;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * JedisPool工具类。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 */
public class JedisPoolUtils {
	
	/**
	 * 执行回调。
	 * 
	 * @param jedisPool Jedis客户端。
	 * @param jedisReturnedCallback 回调。
	 * @return 回调返回值。
	 * @exception Exception 任何可能出现的异常。
	 */
	public static <T> T execute(JedisPool jedisPool, JedisReturnedCallback<T> jedisReturnedCallback) throws Exception {
		Jedis jedis = null;
	    try {   
	    	jedis = jedisPool.getResource();
	        return jedisReturnedCallback.doInJedis(jedis);
	    } finally{
	    	if (jedis != null) {
	    		jedisPool.returnResource(jedis);
	    	}
	    }
	}
	
	/**
	 * 执行回调。
	 * 
	 * @param jedisPool Jedis客户端。
	 * @param jedisCallback 回调。
	 * @exception Exception 任何可能出现的异常。
	 */
	public static void execute(JedisPool jedisPool, JedisCallback jedisCallback) throws Exception {
		Jedis jedis = null;
	    try {   
	    	jedis = jedisPool.getResource();
	    	jedisCallback.doInJedis(jedis);
	    } finally{
	    	if (jedis != null) {
	    		jedisPool.returnResource(jedis);
	    	}
	    }
	}
}
