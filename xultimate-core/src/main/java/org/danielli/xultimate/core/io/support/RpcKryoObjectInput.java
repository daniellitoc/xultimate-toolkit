package org.danielli.xultimate.core.io.support;

import java.io.IOException;
import java.io.InputStream;

import org.danielli.xultimate.core.io.AbstractObjectInput;

import com.esotericsoftware.kryo.Kryo;

/**
 * Kryo序列化机制的对象输入流。
 * 
 * @author Daniel Li
 * @since 10 May 2014
 */
public class RpcKryoObjectInput extends AbstractObjectInput {

	private final Kryo kryo;

	public RpcKryoObjectInput (int bufferSize, Kryo kryo) {
		super(bufferSize);
		this.kryo = kryo;
	}

	public RpcKryoObjectInput (byte[] buffer, Kryo kryo) {
		super(buffer);
		this.kryo = kryo;
	}

	public RpcKryoObjectInput (byte[] buffer, int offset, int count, Kryo kryo) {
		super(buffer, offset, count);
		this.kryo = kryo;
	}

	public RpcKryoObjectInput (InputStream inputStream, Kryo kryo) {
		super(inputStream);
		this.kryo = kryo;
	}

	public RpcKryoObjectInput (InputStream inputStream, int bufferSize, Kryo kryo) {
		super(inputStream, bufferSize);
		this.kryo = kryo;
	}
	
	@Override
	public Object readObject() throws IOException, ClassNotFoundException {
		return kryo.readClassAndObject(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException {
		return (T) kryo.readClassAndObject(this);
	}

}