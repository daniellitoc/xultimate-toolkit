package org.danielli.xultimate.context.chardet;

import org.springframework.core.NestedRuntimeException;

/**
 * 字符集检测器异常。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see NestedRuntimeException
 */
public class CharsetDetectorException extends NestedRuntimeException {
	
	private static final long serialVersionUID = 2303266621170729888L;
	
	public CharsetDetectorException(String message) {
		super(message);
	}
	
	public CharsetDetectorException(String message, Throwable cause) {
		super(message, cause);
	}
}
