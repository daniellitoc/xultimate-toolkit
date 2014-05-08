package org.danielli.xultimate.core.io;

import java.io.IOException;
import java.io.OutputStream;

public abstract class AbstractObjectOutput extends DataOutput {

	public AbstractObjectOutput (int bufferSize) {
		super(bufferSize);
	}

	public AbstractObjectOutput (int bufferSize, int maxBufferSize) {
		super(bufferSize, maxBufferSize);
	}

	public AbstractObjectOutput (byte[] buffer) {
		super(buffer);
	}

	public AbstractObjectOutput (byte[] buffer, int maxBufferSize) {
		super(buffer, maxBufferSize);
	}

	public AbstractObjectOutput (OutputStream outputStream) {
		super(outputStream);
	}

	public AbstractObjectOutput (OutputStream outputStream, int bufferSize) {
		super(outputStream, bufferSize);
	}

	
	public abstract void writeObject(Object obj) throws IOException;
}
