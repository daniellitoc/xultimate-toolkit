package org.danielli.xultimate.core.serializer.kryo;

import java.io.InputStream;
import java.io.OutputStream;

import org.danielli.xultimate.core.serializer.ClassTypeSupporterSerializer;
import org.danielli.xultimate.core.serializer.DeserializerException;
import org.danielli.xultimate.core.serializer.SerializerException;
import org.danielli.xultimate.core.serializer.kryo.KryoGenerator;
import org.danielli.xultimate.core.serializer.kryo.support.ThreadLocalKryoGenerator;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * @deprecated 暂时无用。
 */
public class LocalKryoSerializer implements ClassTypeSupporterSerializer {

	protected int bufferSize = 256;
	
	protected KryoGenerator kryoGenerator = ThreadLocalKryoGenerator.INSTANCE;
	
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	
	public void setKryoGenerator(KryoGenerator kryoGenerator) {
		this.kryoGenerator = kryoGenerator;
	}

	@Override
	public boolean support(Class<?> classType) {
		return true;
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
			kryoGenerator.generate().writeObject(output, source);
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
	
	public <T> T deserialize(Input input, Class<T> clazz) throws DeserializerException {
		try {
			return kryoGenerator.generate().readObject(input, clazz);
		} catch (Exception e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}
}
