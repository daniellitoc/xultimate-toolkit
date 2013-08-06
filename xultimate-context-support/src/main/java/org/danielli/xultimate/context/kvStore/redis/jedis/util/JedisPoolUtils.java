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
	
	public static <T> T execute(JedisPool jedisPool, JedisReturnedCallback<T> jedisReturnedCallback) {
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
	
	public static void execute(JedisPool jedisPool, JedisCallback jedisCallback) {
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
