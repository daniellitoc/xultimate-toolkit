package org.danielli.xultimate.context.kvStore.memcached;

import org.danielli.xultimate.context.kvStore.AbstractKeyValueStoreTemplate;
import org.danielli.xultimate.context.kvStore.KeyValueStoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象Memcached模板类。主要提供异常记录功能。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 */
public abstract class AbstractMemcachedTemplate extends AbstractKeyValueStoreTemplate {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 记录异常行为到日志。日志的记录是以所使用的最终实例类决定的。
	 * 
	 * @param e 异常。
	 */
	@Override
	protected void handleException(KeyValueStoreException e) {
		logger.error(e.getMessage(), e);
	}
	
	@Override
	protected MemcachedException wrapperException(Exception e) {
		return new MemcachedException(e.getMessage(), e);
	}
}
