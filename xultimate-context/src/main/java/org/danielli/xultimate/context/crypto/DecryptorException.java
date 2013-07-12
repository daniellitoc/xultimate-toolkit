package org.danielli.xultimate.context.crypto;

import org.springframework.core.NestedRuntimeException;

/**
 * 解密异常。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see NestedRuntimeException
 */
public class DecryptorException extends NestedRuntimeException {
	
	private static final long serialVersionUID = 8862452193888553128L;
	
    public DecryptorException(String message) {
    	super(message);
    }
	
    public DecryptorException(String message, Throwable cause) {
        super(message, cause);
    }
}
