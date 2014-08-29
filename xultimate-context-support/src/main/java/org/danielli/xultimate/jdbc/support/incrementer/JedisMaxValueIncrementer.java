package org.danielli.xultimate.jdbc.support.incrementer;


import org.danielli.xultimate.context.kvStore.KeyValueStoreException;
import org.danielli.xultimate.context.kvStore.redis.jedis.JedisCallback;
import org.danielli.xultimate.context.kvStore.redis.jedis.support.JedisTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;

import redis.clients.jedis.Jedis;

/**
 * 最大值增长器的Jedis实现。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 *
 */
public class JedisMaxValueIncrementer extends AbstractKeyMaxValueIncrementer {

	private JedisTemplate jedisTemplate;
	
	@Override
	protected long getNextMaxId() throws DataAccessException {
		try {
			return jedisTemplate.execute(new JedisCallback<Long>() {
				@Override
				public Long doInJedis(Jedis jedis) {
					return jedis.incrBy(getKeyName(), getCacheSize() * getStep());
				}
			});
		} catch (KeyValueStoreException exception) {
			throw new DataAccessResourceFailureException(exception.getMessage(), exception);
		}	
	}
	
	public void setJedisTemplate(JedisTemplate jedisTemplate) {
		this.jedisTemplate = jedisTemplate;
	}
}
