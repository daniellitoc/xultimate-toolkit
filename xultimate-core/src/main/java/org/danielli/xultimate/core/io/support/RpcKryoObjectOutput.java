package org.danielli.xultimate.core.io.support;

import java.io.IOException;
import java.io.OutputStream;

import org.danielli.xultimate.core.io.AbstractObjectOutput;

import com.esotericsoftware.kryo.Kryo;

/**
 * Kryo序列化机制的对象输出流。
 * 
 * @author Daniel Li
 * @since 10 May 2014
 */
public class RpcKryoObjectOutput extends AbstractObjectOutput {

	private final Kryo kryo;

	public RpcKryoObjectOutput (int bufferSize, Kryo kryo) {
		super(bufferSize);
		this.kryo = kryo;
	}

	public RpcKryoObjectOutput (int bufferSize, int maxBufferSize, Kryo kryo) {
		super(bufferSize, maxBufferSize);
		this.kryo = kryo;
	}

	public RpcKryoObjectOutput (byte[] buffer, Kryo kryo) {
		super(buffer);
		this.kryo = kryo;
	}

	public RpcKryoObjectOutput (byte[] buffer, int maxBufferSize, Kryo kryo) {
		super(buffer, maxBufferSize);
		this.kryo = kryo;
	}

	public RpcKryoObjectOutput (OutputStream outputStream, Kryo kryo) {
		super(outputStream);
		this.kryo = kryo;
	}

	public RpcKryoObjectOutput (OutputStream outputStream, int bufferSize, Kryo kryo) {
		super(outputStream, bufferSize);
		this.kryo = kryo;
	}
	
	@Override
	public void writeObject(Object obj) throws IOException {
		kryo.writeClassAndObject(this, obj);
	}
}
