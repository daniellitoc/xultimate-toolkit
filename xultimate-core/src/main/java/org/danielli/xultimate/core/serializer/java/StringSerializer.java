package org.danielli.xultimate.core.serializer.java;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.danielli.xultimate.core.serializer.AbstractClassTypeSupportSerializer;
import org.danielli.xultimate.core.serializer.DeserializerException;
import org.danielli.xultimate.core.serializer.SerializerException;
import org.danielli.xultimate.core.serializer.java.util.SerializerUtils;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.reflect.ClassUtils;

public class StringSerializer extends AbstractClassTypeSupportSerializer  {

	@Override
	public boolean support(Class<?> classType) {
		return ClassUtils.isAssignable(String.class, classType);
	}
	
	@Override
	public <T> byte[] serialize(T source) throws SerializerException {
		return StringUtils.getBytesUtf8((String) source);
	}

	@Override
	public <T> void serialize(T source, OutputStream outputStream) throws SerializerException {
		try {
			byte[] result = StringUtils.getBytesUtf8((String) source);
			outputStream.write(SerializerUtils.encodeInt(result.length, false));
			outputStream.write(result);
		} catch (IOException e) {
			throw new SerializerException(e.getMessage(), e);
		}
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws DeserializerException {
		return clazz.cast(StringUtils.newStringUtf8(bytes));
	}

	@Override
	public <T> T deserialize(InputStream inputStream, Class<T> clazz) throws DeserializerException {
		try {
			byte[] result = new byte[SerializerUtils.INT_BYTE_SIZE];
			inputStream.read(result);
			int length = SerializerUtils.decodeInt(result);

			result = new byte[length];
			inputStream.read(result);
			return clazz.cast(StringUtils.newStringUtf8(result));
		} catch (IOException e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}


}
