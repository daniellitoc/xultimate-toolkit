package org.danielli.xultimate.core.serializer;

import java.io.OutputStream;

/**
 * 序列化处理器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public interface Serializer {
	
	/**
	 * 序列化。
	 * 
	 * @param source 需要序列化的对像实例。
	 * @return 序列化后的数据。
	 */
	<T> byte[] serialize(T source) throws SerializerException;
	
	/**
	 * 序列化一个对像到给定的输出流。
	 * 
	 * @param source 需要序列化的对像实例。
	 * @param outputStream 输出流。
	 */
	<T> void serialize(T source, OutputStream outputStream) throws SerializerException;
}
