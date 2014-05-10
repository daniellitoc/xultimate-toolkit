package org.danielli.xultimate.core.io.support;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.danielli.xultimate.core.io.AbstractObjectOutput;

/**
 * Java序列化机制的对象输出流。
 * 
 * @author Daniel Li
 * @since 10 May 2014
 */
public class JavaObjectOutput extends AbstractObjectOutput {

	private final ObjectOutputStream objectOutputStream;

	public JavaObjectOutput (int bufferSize) throws IOException {
		super(bufferSize);
		this.objectOutputStream = new ObjectOutputStream(this);
	}

	public JavaObjectOutput (int bufferSize, int maxBufferSize) throws IOException {
		super(bufferSize, maxBufferSize);
		this.objectOutputStream = new ObjectOutputStream(this);
	}

	public JavaObjectOutput (byte[] buffer) throws IOException {
		super(buffer);
		this.objectOutputStream = new ObjectOutputStream(this);
	}

	public JavaObjectOutput (byte[] buffer, int maxBufferSize) throws IOException {
		super(buffer, maxBufferSize);
		this.objectOutputStream = new ObjectOutputStream(this);
	}

	public JavaObjectOutput (OutputStream outputStream) throws IOException {
		super(outputStream);
		this.objectOutputStream = new ObjectOutputStream(this);
	}

	public JavaObjectOutput (OutputStream outputStream, int bufferSize) throws IOException {
		super(outputStream, bufferSize);
		this.objectOutputStream = new ObjectOutputStream(this);
	}

	
	@Override
	public void writeObject(Object obj) throws IOException {
		this.objectOutputStream.writeObject(obj);
	}

}
