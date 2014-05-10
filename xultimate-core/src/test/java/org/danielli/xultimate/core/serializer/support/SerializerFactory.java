package org.danielli.xultimate.core.serializer.support;

import java.io.OutputStream;

import org.danielli.xultimate.core.serializer.ClassTypeSupporterSerializer;
import org.danielli.xultimate.core.serializer.Serializer;
import org.danielli.xultimate.core.serializer.SerializerException;
import org.danielli.xultimate.util.ArrayUtils;

/**
 * 序列化工厂，通过使用集合来确认所使用的序列化器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @deprecated 暂时无用。
 */
public class SerializerFactory implements Serializer {
	
	/** 序列化器列表 */
	protected ClassTypeSupporterSerializer[] serializers;

	private Serializer getSerializer(Class<?> clazz) {
		if (ArrayUtils.isNotEmpty(serializers)) {
			for (ClassTypeSupporterSerializer serializer : serializers) {
				if (serializer.support(clazz)) {
					return serializer;
				}
			}
		}
		throw new SerializerException("have no serializer can be used");
		
	}

	@Override
	public <T> byte[] serialize(T source) throws SerializerException {
		return getSerializer(source.getClass()).serialize(source);
	}

	@Override
	public <T> void serialize(T source, OutputStream outputStream) throws SerializerException {
		getSerializer(source.getClass()).serialize(source, outputStream);
	}

	/**
	 * 设置序列化器列表。
	 * @param serializers 序列化器列表。
	 */
	public void setSerializers(ClassTypeSupporterSerializer[] serializers) {
		this.serializers = serializers;
	}
}
