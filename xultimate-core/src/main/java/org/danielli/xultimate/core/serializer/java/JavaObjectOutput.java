package org.danielli.xultimate.core.serializer.java;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.danielli.xultimate.core.io.AbstractObjectOutput;

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
