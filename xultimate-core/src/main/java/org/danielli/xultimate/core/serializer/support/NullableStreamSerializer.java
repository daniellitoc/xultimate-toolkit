package org.danielli.xultimate.core.serializer.support;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.danielli.xultimate.core.serializer.Deserializer;
import org.danielli.xultimate.core.serializer.DeserializerException;
import org.danielli.xultimate.core.serializer.Serializer;
import org.danielli.xultimate.core.serializer.SerializerException;
import org.danielli.xultimate.core.serializer.java.util.SerializerUtils;

public class NullableStreamSerializer implements Serializer, Deserializer {

	protected Serializer serializer;
	
	protected Deserializer deserializer;
	
	public NullableStreamSerializer(Serializer serializer, Deserializer deserializer) {
		this.serializer = serializer;
		this.deserializer = deserializer;
	}

	@Override
	public <T> byte[] serialize(T source) throws SerializerException {
		return serializer.serialize(source);
	}

	@Override
	public <T> void serialize(T source, OutputStream outputStream) throws SerializerException {
		try {
			if (source == null) {
				outputStream.write(SerializerUtils.encodeByte((byte) 0));
			} else {
				outputStream.write(SerializerUtils.encodeByte((byte) 1));
				serializer.serialize(source, outputStream);
			}
		} catch (IOException e) {
			throw new SerializerException(e.getMessage(), e);
		}
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws DeserializerException {
		return deserializer.deserialize(bytes, clazz);
	}

	@Override
	public <T> T deserialize(InputStream inputStream, Class<T> clazz) throws DeserializerException {
		try {
			byte[] result = new byte[SerializerUtils.BYTE_BYTE_SIZE];
			inputStream.read(result);
			byte nullable = SerializerUtils.decodeByte(result);
			if (nullable == 0) {
				return null;
			}
			return deserializer.deserialize(inputStream, clazz);
		} catch (IOException e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}
}
