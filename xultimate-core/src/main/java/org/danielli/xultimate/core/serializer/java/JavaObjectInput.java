package org.danielli.xultimate.core.serializer.java;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import org.danielli.xultimate.core.io.AbstractObjectInput;

public class JavaObjectInput extends AbstractObjectInput {

	private final ObjectInputStream objectInputStream;

	public JavaObjectInput (int bufferSize) throws IOException {
		super(bufferSize);
		this.objectInputStream = new ObjectInputStream(this);
	}

	public JavaObjectInput (byte[] buffer) throws IOException {
		super(buffer);
		this.objectInputStream = new ObjectInputStream(this);
	}

	public JavaObjectInput (byte[] buffer, int offset, int count) throws IOException {
		super(buffer, offset, count);
		this.objectInputStream = new ObjectInputStream(this);
	}

	public JavaObjectInput (InputStream inputStream) throws IOException {
		super(inputStream);
		this.objectInputStream = new ObjectInputStream(this);
	}

	public JavaObjectInput (InputStream inputStream, int bufferSize) throws IOException {
		super(inputStream, bufferSize);
		this.objectInputStream = new ObjectInputStream(this);
	}
	
	@Override
	public Object readObject() throws IOException, ClassNotFoundException {
		return this.objectInputStream.readObject();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException {
		return (T) this.objectInputStream.readObject();
	}

}
