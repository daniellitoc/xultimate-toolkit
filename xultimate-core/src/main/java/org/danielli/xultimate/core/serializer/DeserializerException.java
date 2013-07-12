package org.danielli.xultimate.core.serializer;

import org.springframework.core.NestedRuntimeException;

/**
 * 反序列化异常。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see NestedRuntimeException
 */
public class DeserializerException extends NestedRuntimeException {
	
	private static final long serialVersionUID = 3488287486491666049L;

	public DeserializerException(String message) {
    	super(message);
    }
	
    public DeserializerException(String message, Throwable cause) {
        super(message, cause);
    }
}
