package org.danielli.xultimate.context.image;

import org.springframework.core.NestedRuntimeException;

/**
 * 图片异常。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see NestedRuntimeException
 */
public class ImageException extends NestedRuntimeException {
	
	private static final long serialVersionUID = -4680588316992658546L;

    public ImageException(String message) {
    	super(message);
    }
	
    public ImageException(String message, Throwable cause) {
        super(message, cause);
    }
	
}
