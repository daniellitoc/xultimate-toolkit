package org.danielli.xultimate.context.kvStore.memcached;

import org.danielli.xultimate.context.kvStore.AbstractKeyValueStoreTemplate;
import org.danielli.xultimate.context.kvStore.KeyValueStoreException;

/**
 * 抽象Memcached模板类。主要提供异常记录功能。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 */
public abstract class AbstractMemcachedTemplate extends AbstractKeyValueStoreTemplate {
	
	/**
	 * 抛出异常。
	 */
	@Override
	protected void handleException(KeyValueStoreException e) {
		throw e;
	}
	
	@Override
	protected MemcachedException wrapperException(Exception e) {
		if (e instanceof MemcachedException) {
			return (MemcachedException) e;
		}
		return new MemcachedException(e.getMessage(), e);
	}
}
