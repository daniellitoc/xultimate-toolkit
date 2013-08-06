package org.danielli.xultimate.context.kvStore;

import org.springframework.core.NestedRuntimeException;

/**
 * K/V存储异常。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 * @see NestedRuntimeException
 */
public abstract class KeyValueStoreException extends NestedRuntimeException {
	private static final long serialVersionUID = -5363328862858087052L;

	public KeyValueStoreException(String message) {
		super(message);
	}
	
	public KeyValueStoreException(String message, Throwable cause) {
		super(message, cause);
	}

}
