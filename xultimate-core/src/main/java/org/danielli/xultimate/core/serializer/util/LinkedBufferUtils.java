package org.danielli.xultimate.core.serializer.util;

import com.dyuproject.protostuff.LinkedBuffer;

/**
 * LinkedBuffer工具类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class LinkedBufferUtils {
	
	private static ThreadLocal<LinkedBuffer> linkedBuffer = new ThreadLocal<LinkedBuffer>() {
        protected LinkedBuffer initialValue() {
            return LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        };
    };

	/**
	 * 获取LinkedBuffer实例。
	 * @return LinkedBuffer实例。
	 */
    public static LinkedBuffer getLinkedBuffer() {
        LinkedBuffer buffer = linkedBuffer.get();
        buffer.clear();
        return buffer;
    }
}
