package org.danielli.xultimate.core.serializer.kryo;

import java.io.InputStream;
import java.io.OutputStream;

import org.danielli.xultimate.core.serializer.DeserializerException;
import org.danielli.xultimate.core.serializer.RpcSerializer;
import org.danielli.xultimate.core.serializer.SerializerException;
import org.danielli.xultimate.core.serializer.kryo.support.ThreadLocalKryoGenerator;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * Kryo序列化实现。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class RpcKryoSerializer extends RpcSerializer {

	protected int bufferSize = 256;
	
	protected KryoGenerator kryoGenerator = ThreadLocalKryoGenerator.INSTANCE;
	
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	
	public void setKryoGenerator(KryoGenerator kryoGenerator) {
		this.kryoGenerator = kryoGenerator;
	}
	
	@Override
	public <T> byte[] serialize(T source) throws SerializerException {
		Output output = new Output(bufferSize, -1);
		serialize(source, output);
		return output.toBytes();
	}

	@Override
	public <T> void serialize(T source, OutputStream outputStream) throws SerializerException {
		if (!(outputStream instanceof Output)) {
			throw new SerializerException("Parameter 'outputStream' must be com.esotericsoftware.kryo.io.Output type");
		}
		serialize(source, (Output) outputStream);
	}
	
	public <T> void serialize(T source, Output output) throws SerializerException {
		try {
			kryoGenerator.generate().writeClassAndObject(output, source);
		} catch (Exception e) {
			throw new SerializerException(e.getMessage(), e);
		}
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws DeserializerException {
		Input input = new Input(bytes);
		return deserialize(input, clazz);
	}

	@Override
	public <T> T deserialize(InputStream inputStream, Class<T> clazz) throws DeserializerException {
		if (!(inputStream instanceof Input)) {
			throw new SerializerException("Parameter 'inputStream' must be com.esotericsoftware.kryo.io.Input type");
		}
		return deserialize((Input) inputStream, clazz);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T deserialize(Input input, Class<T> clazz) throws DeserializerException {
		try {
			return (T) kryoGenerator.generate().readClassAndObject(input);
		} catch (Exception e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}
}
