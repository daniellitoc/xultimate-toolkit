package org.danielli.xultimate.core.serializer.kryo;

import java.io.InputStream;
import java.io.OutputStream;

import org.danielli.xultimate.core.serializer.AbstractClassTypeSupportSerializer;
import org.danielli.xultimate.core.serializer.DeserializerException;
import org.danielli.xultimate.core.serializer.SerializerException;
import org.danielli.xultimate.core.serializer.kryo.util.KryoUtils;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class MainKryoSerializer extends AbstractClassTypeSupportSerializer {
	
	private static final int DEFAULT_BUFFER_SIZE = 100 * 1024;

	@Override
	public boolean support(Class<?> classType) {
		return true;
	}
	
	@Override
	public <T> byte[] serialize(T source) throws SerializerException {
		Output output = new Output(DEFAULT_BUFFER_SIZE);
		try {
			KryoUtils.getKryo().writeObject(output, source);
		} catch (Exception e) {
			throw new SerializerException(e.getMessage(), e);
		} finally {
			output.flush();
		}
		return output.toBytes();
	}

	@Override
	public <T> void serialize(T source, OutputStream outputStream) throws SerializerException {
		Output output = new Output(DEFAULT_BUFFER_SIZE);
		try {
			KryoUtils.getKryo().writeObject(output, source);
			outputStream.write(output.toBytes());
		} catch (Exception e) {
			throw new SerializerException(e.getMessage(), e);
		} finally {
			output.flush();
		}
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws DeserializerException {
		try {
			Input input = new Input(bytes);
	        return KryoUtils.getKryo().readObject(input, clazz);
		} catch (Exception e) {
			throw new DeserializerException(e.getMessage(), e);
		}
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
			return KryoUtils.getKryo().readObject(input, clazz);
		} catch (Exception e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}

}
