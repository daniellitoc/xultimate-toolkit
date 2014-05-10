package org.danielli.xultimate.core.io;

import java.io.IOException;
import java.io.InputStream;

import com.esotericsoftware.kryo.io.Input;

/**
 * 抽象对象输入流。
 * 
 * @author Daniel Li
 * @since 10 May 2014
 */
public abstract class AbstractObjectInput extends Input {

	public AbstractObjectInput (int bufferSize) {
		super(bufferSize);
	}

	public AbstractObjectInput (byte[] buffer) {
		super(buffer);
	}

	public AbstractObjectInput (byte[] buffer, int offset, int count) {
		super(buffer, offset, count);
	}

	public AbstractObjectInput (InputStream inputStream, int bufferSize) {
		super(inputStream, bufferSize);
	}
	
	public abstract Object readObject() throws IOException, ClassNotFoundException;
	
	public abstract <T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException;

}
