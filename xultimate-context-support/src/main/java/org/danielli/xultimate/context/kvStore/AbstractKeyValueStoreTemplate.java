package org.danielli.xultimate.context.kvStore;

/**
 * 抽象K/V存储模板类。主要提供异常处理功能。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 */
public abstract class AbstractKeyValueStoreTemplate {
	
	/**
	 * 处理异常。
	 * 
	 * @param e 异常。
	 */
	protected abstract void handleException(KeyValueStoreException e);
	
	/**
	 * 包装异常。
	 * @param e 实际异常。 
	 * @return 包装后的异常。
	 */
	protected abstract KeyValueStoreException wrapperException(Exception e);
}
