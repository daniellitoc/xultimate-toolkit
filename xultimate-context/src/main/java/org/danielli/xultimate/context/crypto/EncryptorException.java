package org.danielli.xultimate.context.crypto;

import org.springframework.core.NestedRuntimeException;

/**
 * 加密异常。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see NestedRuntimeException
 */
public class EncryptorException extends NestedRuntimeException {
	
	private static final long serialVersionUID = 8862452193888553128L;
	
    public EncryptorException(String message) {
    	super(message);
    }
	
    public EncryptorException(String message, Throwable cause) {
        super(message, cause);
    }
}
