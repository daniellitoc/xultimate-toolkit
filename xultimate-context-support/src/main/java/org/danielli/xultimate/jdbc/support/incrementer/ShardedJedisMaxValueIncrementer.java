package org.danielli.xultimate.jdbc.support.incrementer;

import org.danielli.xultimate.context.kvStore.KeyValueStoreException;
import org.danielli.xultimate.context.kvStore.redis.jedis.ShardedJedisCallback;
import org.danielli.xultimate.context.kvStore.redis.jedis.support.ShardedJedisTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;

import redis.clients.jedis.ShardedJedis;

/**
 * 最大值增长器的Jedis实现。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 *
 */
public class ShardedJedisMaxValueIncrementer extends AbstractKeyMaxValueIncrementer {
	
	private ShardedJedisTemplate shardedJedisTemplate;

	@Override
	protected long getNextMaxId() throws DataAccessException {
		try {
			return shardedJedisTemplate.execute(new ShardedJedisCallback<Long>() {

				@Override
				public Long doInShardedJedis(ShardedJedis shardedJedis) {
					return shardedJedis.incrBy(getKeyName(), getCacheSize() * getStep());
				}
				
			});
		} catch (KeyValueStoreException exception) {
			throw new DataAccessResourceFailureException(exception.getMessage(), exception);
		}
	}

	public void setShardedJedisTemplate(ShardedJedisTemplate shardedJedisTemplate) {
		this.shardedJedisTemplate = shardedJedisTemplate;
	}
}
