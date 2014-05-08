package org.danielli.xultimate.core.serializer.kryo;

import java.io.IOException;
import java.io.OutputStream;

import org.danielli.xultimate.core.io.AbstractObjectOutput;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;

public class KryoObjectOutput extends AbstractObjectOutput {

	private final Kryo kryo;
	
	private final Output output;

	public KryoObjectOutput (int bufferSize, Kryo kryo) throws IOException {
		super(bufferSize);
		this.kryo = kryo;
		this.output = new Output(this, bufferSize);
	}

	public KryoObjectOutput (int bufferSize, int maxBufferSize, Kryo kryo) throws IOException {
		super(bufferSize, maxBufferSize);
		this.kryo = kryo;
		this.output = new Output(this, bufferSize);
	}

	public KryoObjectOutput (byte[] buffer, Kryo kryo) throws IOException {
		super(buffer);
		this.kryo = kryo;
		this.output = new Output(this, buffer.length);
	}

	public KryoObjectOutput (byte[] buffer, int maxBufferSize, Kryo kryo) throws IOException {
		super(buffer, maxBufferSize);
		this.kryo = kryo;
		this.output = new Output(this, buffer.length);
	}

	public KryoObjectOutput (OutputStream outputStream, Kryo kryo) throws IOException {
		super(outputStream);
		this.kryo = kryo;
		this.output = new Output(this, 256);
	}

	public KryoObjectOutput (OutputStream outputStream, int bufferSize, Kryo kryo) throws IOException {
		super(outputStream, bufferSize);
		this.kryo = kryo;
		this.output = new Output(this, bufferSize);
	}
	
	@Override
	public void writeObject(Object obj) throws IOException {
		try {
			kryo.writeClassAndObject(output, obj);
		} finally {
			output.flush();
			output.clear();
		}
	}

}
