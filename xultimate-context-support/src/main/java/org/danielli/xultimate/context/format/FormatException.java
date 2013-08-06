package org.danielli.xultimate.context.format;

import org.springframework.core.NestedRuntimeException;

/**
 * 格式化异常。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see NestedRuntimeException
 */
public class FormatException extends NestedRuntimeException {
	
	private static final long serialVersionUID = 2303266621170729888L;
	
	public FormatException(String message) {
		super(message);
	}
	
	public FormatException(String message, Throwable cause) {
		super(message, cause);
	}
}
