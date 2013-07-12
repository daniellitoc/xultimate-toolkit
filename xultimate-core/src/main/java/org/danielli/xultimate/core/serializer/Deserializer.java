package org.danielli.xultimate.core.serializer;

import java.io.InputStream;

/**
 * 反序列化处理器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public interface Deserializer {
	
	/**
	 * 反序列化。
	 * 
	 * @param bytes 序列化数据。
	 * @param clazz 返序列化后的类型。
	 * @return 类型为clazz的实例。
	 */
	<T> T deserialize(byte[] bytes, Class<T> clazz) throws DeserializerException;

	/**
	 * 从输入流中反序列化。
	 * 
	 * @param inputStream 输入流。
	 * @param clazz 返序列化后的类型。
	 * @return 类型为clazz的实例。
	 */
	<T> T deserialize(InputStream inputStream,  Class<T> clazz) throws DeserializerException;
}
