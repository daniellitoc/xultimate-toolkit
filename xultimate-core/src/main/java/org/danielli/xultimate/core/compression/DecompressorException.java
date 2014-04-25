package org.danielli.xultimate.core.compression;

import org.springframework.core.NestedRuntimeException;

/**
 * 解压缩异常。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see NestedRuntimeException
 */
public class DecompressorException extends NestedRuntimeException {
	
	private static final long serialVersionUID = 8862452193888553128L;
	
    public DecompressorException(String message) {
    	super(message);
    }
	
    public DecompressorException(String message, Throwable cause) {
        super(message, cause);
    }
}
