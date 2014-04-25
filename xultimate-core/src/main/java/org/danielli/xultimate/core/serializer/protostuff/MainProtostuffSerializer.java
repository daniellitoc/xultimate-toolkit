package org.danielli.xultimate.core.serializer.protostuff;

import java.io.InputStream;
import java.io.OutputStream;

import org.danielli.xultimate.core.serializer.AbstractClassTypeSupporterSerializer;
import org.danielli.xultimate.core.serializer.DeserializerException;
import org.danielli.xultimate.core.serializer.SerializerException;
import org.danielli.xultimate.core.serializer.protostuff.util.LinkedBufferUtils;
import org.danielli.xultimate.core.serializer.protostuff.util.SchemaUtils;
import org.danielli.xultimate.util.reflect.BeanUtils;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;

/**
 * Protobuf主序列化和反序列化处理类。性能比手动优化差，和手动正常操作的性能差一点。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * 
 */
public class MainProtostuffSerializer extends AbstractClassTypeSupporterSerializer {
	
	protected int bufferSize = 10 * 1024;
	
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	
	@Override
	public boolean support(Class<?> classType) {
		return classType.isArray() ? false : super.support(classType);
	}
	
	@Override
	public <T> byte[] serialize(T source) throws SerializerException {
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) source.getClass();
		LinkedBuffer buffer = LinkedBufferUtils.getCurrentLinkedBuffer(bufferSize);
		try {
			return ProtostuffIOUtil.toByteArray(source, SchemaUtils.getSchema(clazz), buffer);
		} catch (Exception e) {
			throw new SerializerException(e.getMessage(), e);
		} finally {
			buffer.clear();
		}
	}
	
	@Override
	public <T> void serialize(T source, OutputStream outputStream) throws SerializerException {
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) source.getClass();
		LinkedBuffer buffer = LinkedBufferUtils.getCurrentLinkedBuffer(bufferSize);
		try {
			ProtostuffIOUtil.writeDelimitedTo(outputStream, source, SchemaUtils.getSchema(clazz), buffer);
		} catch (Exception e) {
			throw new SerializerException(e.getMessage(), e);
		} finally {
			buffer.clear();
		}
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws DeserializerException {
		try {
			T result = BeanUtils.instantiate(clazz);
			ProtostuffIOUtil.mergeFrom(bytes, result, SchemaUtils.getSchema(clazz));
			return result;
		} catch (Exception e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}

	@Override
	public <T> T deserialize(InputStream inputStream, Class<T> clazz) throws DeserializerException {
		try {
			T result = BeanUtils.instantiate(clazz);;
			ProtostuffIOUtil.mergeDelimitedFrom(inputStream, result, SchemaUtils.getSchema(clazz));
			return result;
		} catch (Exception e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}
}
