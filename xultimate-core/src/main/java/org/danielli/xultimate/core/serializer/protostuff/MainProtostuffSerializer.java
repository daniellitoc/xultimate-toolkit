package org.danielli.xultimate.core.serializer.protostuff;

import java.io.InputStream;
import java.io.OutputStream;

import org.danielli.xultimate.core.serializer.AbstractClassTypeSupportSerializer;
import org.danielli.xultimate.core.serializer.DeserializerException;
import org.danielli.xultimate.core.serializer.SerializerException;
import org.danielli.xultimate.core.serializer.util.LinkedBufferUtils;
import org.danielli.xultimate.core.serializer.util.SchemaUtils;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;

/**
 * Protobuf主序列化和反序列化处理类。性能比手动优化差一点，和手动正常操作的性能差不多。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * 
 */
public class MainProtostuffSerializer extends AbstractClassTypeSupportSerializer {
	
	@Override
	public <T> byte[] serialize(T source) throws SerializerException {
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) source.getClass();
		LinkedBuffer buffer = LinkedBufferUtils.getLinkedBuffer();
		try {
			Schema<T> schema = SchemaUtils.getSchema(clazz);
			return ProtostuffIOUtil.toByteArray(source, schema, buffer);
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
		LinkedBuffer buffer = LinkedBufferUtils.getLinkedBuffer();
		try {
			Schema<T> schema = SchemaUtils.getSchema(clazz);
			ProtostuffIOUtil.writeTo(outputStream, source, schema, buffer);
		} catch (Exception e) {
			throw new SerializerException(e.getMessage(), e);
		} finally {
			buffer.clear();
		}
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws DeserializerException {
		try {
			T result = clazz.newInstance();
			ProtostuffIOUtil.mergeFrom(bytes, result, SchemaUtils.getSchema(clazz));
			return result;
		} catch (Exception e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}

	@Override
	public <T> T deserialize(InputStream inputStream, Class<T> clazz) throws DeserializerException {
		try {
			T result = clazz.newInstance();
			ProtostuffIOUtil.mergeFrom(inputStream, result, SchemaUtils.getSchema(clazz));
			return result;
		} catch (Exception e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}
}
