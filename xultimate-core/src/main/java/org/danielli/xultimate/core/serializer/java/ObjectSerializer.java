package org.danielli.xultimate.core.serializer.java;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;
import org.danielli.xultimate.core.serializer.DeserializerException;
import org.danielli.xultimate.core.serializer.RpcSerializer;
import org.danielli.xultimate.core.serializer.SerializerException;

public class ObjectSerializer extends RpcSerializer {

	@Override
	public <T> byte[] serialize(T source) throws SerializerException {
		try {
			return SerializationUtils.serialize((Serializable) source);
		} catch (Exception e) {
			throw new SerializerException(e.getMessage(), e);
		}
	}

	@Override
	public <T> void serialize(T source, OutputStream outputStream) throws SerializerException {
		try {
			SerializationUtils.serialize((Serializable) source, outputStream);
		} catch (Exception e) {
			throw new SerializerException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws DeserializerException {
		try {
			return (T) SerializationUtils.deserialize(bytes);
		} catch (Exception e) {
			throw new SerializerException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(InputStream inputStream, Class<T> clazz) throws DeserializerException {
		try {
			return (T) SerializationUtils.deserialize(inputStream);
		} catch (Exception e) {
			throw new SerializerException(e.getMessage(), e);
		}
	}

}
