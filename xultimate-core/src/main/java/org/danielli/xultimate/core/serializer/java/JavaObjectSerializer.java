package org.danielli.xultimate.core.serializer.java;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.danielli.xultimate.core.serializer.DeserializerException;
import org.danielli.xultimate.core.serializer.RpcSerializer;
import org.danielli.xultimate.core.serializer.SerializerException;

/**
 * Java序列化实现。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class JavaObjectSerializer extends RpcSerializer {

	protected int bufferSize = 256;
	
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	
	@Override
	public <T> byte[] serialize(T source) throws SerializerException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bufferSize);
		serialize(source, outputStream);
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
				out.flush();
			} catch (IOException e) {
				throw new SerializerException(e.getMessage(), e);
			}
		}
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws DeserializerException {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		return (T) deserialize(inputStream, clazz);
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
		}
	}
}
