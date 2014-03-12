package org.danielli.xultimate.core.serializer.kryo;

import java.io.InputStream;
import java.io.OutputStream;

import org.danielli.xultimate.core.serializer.DeserializerException;
import org.danielli.xultimate.core.serializer.RpcSerializer;
import org.danielli.xultimate.core.serializer.SerializerException;
import org.danielli.xultimate.core.serializer.kryo.util.KryoUtils;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class RpcKryoSerializer extends RpcSerializer {

	private int defaultBufferSize = 10 * 1024;
	
	@Override
	public <T> byte[] serialize(T source) throws SerializerException {
		Output output = new Output(defaultBufferSize);
		try {
			KryoUtils.getKryo().writeClassAndObject(output, source);
		} catch (Exception e) {
			throw new SerializerException(e.getMessage(), e);
		} finally {
			output.flush();
		}
		return output.toBytes();
	}

	@Override
	public <T> void serialize(T source, OutputStream outputStream) throws SerializerException {
		Output output = new Output(defaultBufferSize);
		try {
			KryoUtils.getKryo().writeClassAndObject(output, source);
			outputStream.write(output.toBytes());
		} catch (Exception e) {
			throw new SerializerException(e.getMessage(), e);
		} finally {
			output.flush();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws DeserializerException {
		try {
			Input input = new Input(bytes);
	        return (T) KryoUtils.getKryo().readClassAndObject(input);
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
	
	@SuppressWarnings("unchecked")
	public <T> T deserialize(Input input, Class<T> clazz) throws DeserializerException {
		try {
	        return  (T) KryoUtils.getKryo().readClassAndObject(input);
		} catch (Exception e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}

	public int getDefaultBufferSize() {
		return defaultBufferSize;
	}

	public void setDefaultBufferSize(int defaultBufferSize) {
		this.defaultBufferSize = defaultBufferSize;
	}

}
