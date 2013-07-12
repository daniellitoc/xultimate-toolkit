package org.danielli.xultimate.core.serializer.protostuff;

import java.io.InputStream;
import java.io.OutputStream;

import org.danielli.xultimate.beans.support.ObjectHolder;
import org.danielli.xultimate.core.serializer.AbstractClassTypeSupportSerializer;
import org.danielli.xultimate.core.serializer.DeserializerException;
import org.danielli.xultimate.core.serializer.SerializerException;
import org.danielli.xultimate.core.serializer.util.LinkedBufferUtils;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * Protobuf序列化和反序列化处理类。性能使用手动IO操作差不多。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * 
 */
public class StandbyProtostuffSerializer extends AbstractClassTypeSupportSerializer {
	
	@SuppressWarnings("rawtypes")
	private final Schema<ObjectHolder> schema = RuntimeSchema.createFrom(ObjectHolder.class); 
	
	@Override
	public <T> byte[] serialize(T source) throws SerializerException {
		ObjectHolder<T> holder = new ObjectHolder<T>(source);
		LinkedBuffer buffer = LinkedBufferUtils.getLinkedBuffer();
		try {
			return ProtostuffIOUtil.toByteArray(holder, schema, buffer);
		} catch (Exception e) {
			throw new SerializerException(e.getMessage(), e);
		} finally {
			buffer.clear();
		}
		
	}
	
	@Override
	public <T> void serialize(T source, OutputStream outputStream) throws SerializerException {
		ObjectHolder<T> holder = new ObjectHolder<T>(source);
		LinkedBuffer buffer = LinkedBufferUtils.getLinkedBuffer();
		try {
			ProtostuffIOUtil.writeTo(outputStream, holder, schema, buffer);
		} catch (Exception e) {
			throw new SerializerException(e.getMessage(), e);
		} finally {
			buffer.clear();
		}
	}
	
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws DeserializerException {
		ObjectHolder<T> holder = new ObjectHolder<T>();
		try {
			ProtostuffIOUtil.mergeFrom(bytes, holder, schema);
		} catch (Exception e) {
			throw new DeserializerException(e.getMessage(), e);
		}
		return holder.getObject();
	}

	@Override
	public <T> T deserialize(InputStream inputStream, Class<T> clazz) throws DeserializerException {
		ObjectHolder<T> holder = new ObjectHolder<T>();
		try {
			ProtostuffIOUtil.mergeFrom(inputStream, holder, schema);
		} catch (Exception e) {
			throw new DeserializerException(e.getMessage(), e);
		}
		return holder.getObject();
	}
}