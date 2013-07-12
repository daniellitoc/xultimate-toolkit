package org.danielli.xultimate.util.crypto;

import org.springframework.core.NestedRuntimeException;

/**
 * 加密异常类。
 *
 * @author Daniel Li
 * @since 16 Jun 2013
 */
public class CryptoException extends NestedRuntimeException {
	
	private static final long serialVersionUID = 8862452193888553128L;
	
    public CryptoException(String message) {
    	super(message);
    }
	
    public CryptoException(String message, Throwable cause) {
        super(message, cause);
    }
}
