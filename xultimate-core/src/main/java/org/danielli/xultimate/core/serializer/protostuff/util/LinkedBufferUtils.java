package org.danielli.xultimate.core.serializer.protostuff.util;

import com.dyuproject.protostuff.LinkedBuffer;

/**
 * LinkedBuffer工具类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class LinkedBufferUtils {
	
	private static ThreadLocal<LinkedBuffer> currentLinkedBuffer = new ThreadLocal<LinkedBuffer>();

	/**
	 * 获取LinkedBuffer实例。
	 * @return LinkedBuffer实例。
	 */
    public static LinkedBuffer getCurrentLinkedBuffer(int bufferSize) {
    	LinkedBuffer linkedBuffer = currentLinkedBuffer.get();
    	if (linkedBuffer == null) {
    		linkedBuffer = LinkedBuffer.allocate(bufferSize);
    		currentLinkedBuffer.set(linkedBuffer);
    	}
        return linkedBuffer;
    }
}
