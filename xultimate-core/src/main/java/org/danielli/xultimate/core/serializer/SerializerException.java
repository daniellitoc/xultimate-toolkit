package org.danielli.xultimate.core.serializer;

import org.springframework.core.NestedRuntimeException;

/**
 * 序列化异常。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see NestedRuntimeException
 */
public class SerializerException extends NestedRuntimeException {
	
	private static final long serialVersionUID = 3488287486491666049L;

	public SerializerException(String message) {
    	super(message);
    }
	
    public SerializerException(String message, Throwable cause) {
        super(message, cause);
    }
}
