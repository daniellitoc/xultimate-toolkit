package org.danielli.xultimate.core.json;

import org.springframework.core.NestedRuntimeException;

/**
 * JSON异常。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see NestedRuntimeException
 */
public class JSONException extends NestedRuntimeException {
	
	private static final long serialVersionUID = -9198606590046525595L;
	
	public JSONException(String message) {
		super(message);
	}
	
	public JSONException(String message, Throwable cause) {
		super(message, cause);
	}
}
