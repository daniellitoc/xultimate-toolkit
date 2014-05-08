package org.danielli.xultimate.core.io;

import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractObjectInput extends DataInput {

	public AbstractObjectInput (int bufferSize) {
		super(bufferSize);
	}

	public AbstractObjectInput (byte[] buffer) {
		super(buffer);
	}

	public AbstractObjectInput (byte[] buffer, int offset, int count) {
		super(buffer, offset, count);
	}

	public AbstractObjectInput (InputStream inputStream) {
		super(inputStream);
	}

	public AbstractObjectInput (InputStream inputStream, int bufferSize) {
		super(inputStream, bufferSize);
	}
	
	public abstract Object readObject() throws IOException, ClassNotFoundException;
	
	public abstract <T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException;

}
