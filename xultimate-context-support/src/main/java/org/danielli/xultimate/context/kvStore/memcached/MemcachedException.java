package org.danielli.xultimate.context.kvStore.memcached;

import org.danielli.xultimate.context.kvStore.KeyValueStoreException;

/**
 * Memcached异常。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 * @see KeyValueStoreException
 */
public class MemcachedException extends KeyValueStoreException {

	private static final long serialVersionUID = 1723710559096203019L;

	public MemcachedException(String message) {
		super(message);
	}
	
	public MemcachedException(String message, Throwable cause) {
		super(message, cause);
	}
}
