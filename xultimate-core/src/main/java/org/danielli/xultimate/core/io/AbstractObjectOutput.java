package org.danielli.xultimate.core.io;

import java.io.IOException;
import java.io.OutputStream;

import com.esotericsoftware.kryo.io.Output;

/**
 * 抽象对象输出流。
 * 
 * @author Daniel Li
 * @since 10 May 2014
 */
public abstract class AbstractObjectOutput extends Output {

	/**
	 * maxBufferSize默认为-1，为最大值。
	 */
	public AbstractObjectOutput (int bufferSize) {
		this(bufferSize, -1);
	}

	public AbstractObjectOutput (int bufferSize, int maxBufferSize) {
		super(bufferSize, maxBufferSize);
	}

	/**
	 * maxBufferSize默认为buffer的长度。
	 */
	public AbstractObjectOutput (byte[] buffer) {
		this(buffer, buffer.length);
	}

	public AbstractObjectOutput (byte[] buffer, int maxBufferSize) {
		super(buffer, maxBufferSize);
	}

	/**
	 * maxBufferSize默认为-1，为最大值。
	 */
	public AbstractObjectOutput (OutputStream outputStream, int bufferSize) {
		this(outputStream, bufferSize, -1);
	}

	public AbstractObjectOutput (OutputStream outputStream, int bufferSize, int maxBufferSize) {
		super(bufferSize, maxBufferSize);
		if (outputStream == null) throw new IllegalArgumentException("outputStream cannot be null.");
		setOutputStream(outputStream);
	}
	
	public abstract void writeObject(Object obj) throws IOException;
}
