package org.danielli.xultimate.core.serializer.support;

import java.io.InputStream;

import org.danielli.xultimate.core.serializer.AbstractClassTypeSupportSerializer;
import org.danielli.xultimate.core.serializer.Deserializer;
import org.danielli.xultimate.core.serializer.DeserializerException;
import org.danielli.xultimate.util.ArrayUtils;

/**
 * 解序列化工厂，通过使用集合来确认所使用的解序列化器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class DeserializerFactory implements Deserializer {
	
	/** 解序列化器列表 */
	private AbstractClassTypeSupportSerializer[] deserializers;
	
	private Deserializer getDeserializer(Class<?> clazz) {
		if (ArrayUtils.isNotEmpty(deserializers)) {
			for (AbstractClassTypeSupportSerializer deserializer : deserializers) {
				if (deserializer.support(clazz)) {
					return deserializer;
				}
			}
		}
		throw new DeserializerException("have no deserializer can be used");
	}
	
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws DeserializerException {
		return getDeserializer(clazz).deserialize(bytes, clazz);
	}

	@Override
	public <T> T deserialize(InputStream inputStream, Class<T> clazz) throws DeserializerException {
		return getDeserializer(clazz).deserialize(inputStream, clazz);
	}

	/**
	 * 设置解序列化器列表。
	 * @param deserializers 解序列化器列表。
	 */
	public void setDeserializers(AbstractClassTypeSupportSerializer[] deserializers) {
		this.deserializers = deserializers;
	}
}
