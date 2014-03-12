package org.danielli.xultimate.core.serializer.protostuff.util;

import com.dyuproject.protostuff.LinkedBuffer;

/**
 * LinkedBuffer工具类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class LinkedBufferUtils {
	
	public static final int DEFAULT_BUFFER_SIZE = 10 * 1024;
	
	private static ThreadLocal<LinkedBuffer> linkedBuffer = new ThreadLocal<LinkedBuffer>() {
        protected LinkedBuffer initialValue() {
            return LinkedBuffer.allocate(DEFAULT_BUFFER_SIZE);
        };
    };

	/**
	 * 获取LinkedBuffer实例。
	 * @return LinkedBuffer实例。
	 */
    public static LinkedBuffer getLinkedBuffer() {
        return linkedBuffer.get().clear();
        
    }
}
