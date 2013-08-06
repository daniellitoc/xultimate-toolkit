package org.danielli.xultimate.context.kvStore.redis;

import org.danielli.xultimate.context.kvStore.KeyValueStoreException;

/**
 * Redis异常。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 * @see KeyValueStoreException
 */
public class RedisException extends KeyValueStoreException {

	private static final long serialVersionUID = 1723710559096203019L;

	public RedisException(String message) {
		super(message);
	}
	
	public RedisException(String message, Throwable cause) {
		super(message, cause);
	}
}
