package org.danielli.xultimate.core.serializer.java;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.danielli.xultimate.core.serializer.DeserializerException;
import org.danielli.xultimate.core.serializer.RpcSerializer;
import org.danielli.xultimate.core.serializer.SerializerException;
import org.danielli.xultimate.util.reflect.ClassUtils;

public class ObjectSerializer extends RpcSerializer {

	protected int bufferSize = 10 * 1024;
	
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	
	@Override
	public boolean support(Class<?> classType) {
		return ClassUtils.isAssignable(Serializable.class, classType);
	}
	
	@Override
	public <T> byte[] serialize(T source) throws SerializerException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bufferSize);
		try {
			serialize(source, outputStream);
		} catch (Exception e) {
			throw new SerializerException(e.getMessage(), e);
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				throw new SerializerException(e.getMessage(), e);
			}
		}
		return outputStream.toByteArray();
	}

	@Override
	public <T> void serialize(T source, OutputStream outputStream) throws SerializerException {
		ObjectOutputStream out = null;
		try {
	        out = new ObjectOutputStream(outputStream);
	        out.writeObject(source);
		} catch (Exception e) {
			throw new SerializerException(e.getMessage(), e);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				throw new SerializerException(e.getMessage(), e);
			}
		}
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws DeserializerException {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		try {
			return (T) deserialize(inputStream, clazz);
		} catch (Exception e) {
			throw new SerializerException(e.getMessage(), e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				throw new SerializerException(e.getMessage(), e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(InputStream inputStream, Class<T> clazz) throws DeserializerException {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(inputStream);
            return (T) in.readObject();
		} catch (Exception e) {
			throw new SerializerException(e.getMessage(), e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				throw new SerializerException(e.getMessage(), e);
			}
		}
	}
}
