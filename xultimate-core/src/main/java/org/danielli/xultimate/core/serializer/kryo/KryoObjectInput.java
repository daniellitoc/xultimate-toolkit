package org.danielli.xultimate.core.serializer.kryo;

import java.io.IOException;
import java.io.InputStream;

import org.danielli.xultimate.core.io.AbstractObjectInput;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

public class KryoObjectInput extends AbstractObjectInput {

	private final Kryo kryo;
	
	private final Input input;

	public KryoObjectInput (int bufferSize, Kryo kryo) throws IOException {
		super(bufferSize);
		this.kryo = kryo;
		this.input = new Input(this, bufferSize);
	}

	public KryoObjectInput (byte[] buffer, Kryo kryo) throws IOException {
		super(buffer);
		this.kryo = kryo;
		this.input = new Input(this, buffer.length);
	}

	public KryoObjectInput (byte[] buffer, int offset, int count, Kryo kryo) throws IOException {
		super(buffer, offset, count);
		this.kryo = kryo;
		this.input = new Input(this, buffer.length);
	}

	public KryoObjectInput (InputStream inputStream, Kryo kryo) throws IOException {
		super(inputStream);
		this.kryo = kryo;
		this.input = new Input(this, 256);
	}

	public KryoObjectInput (InputStream inputStream, int bufferSize, Kryo kryo) throws IOException {
		super(inputStream, bufferSize);
		this.kryo = kryo;
		this.input = new Input(this, bufferSize);
	}
	
	@Override
	public Object readObject() throws IOException, ClassNotFoundException {
		return kryo.readClassAndObject(input);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException {
		return (T) kryo.readClassAndObject(input);
	}

}