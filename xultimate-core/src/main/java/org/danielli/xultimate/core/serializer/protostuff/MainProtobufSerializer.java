package org.danielli.xultimate.core.serializer.protostuff;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.danielli.xultimate.core.serializer.AbstractClassTypeSupportSerializer;
import org.danielli.xultimate.core.serializer.DeserializerException;
import org.danielli.xultimate.core.serializer.SerializerException;
import org.danielli.xultimate.core.serializer.protostuff.util.LinkedBufferUtils;
import org.danielli.xultimate.core.serializer.protostuff.util.SchemaUtils;
import org.danielli.xultimate.util.reflect.BeanUtils;
import org.danielli.xultimate.util.reflect.ClassUtils;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;

/**
 * Protobuf主序列化和反序列化处理类。性能比手动优化差，和手动正常操作的性能差一点。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * 
 */
public class MainProtobufSerializer extends AbstractClassTypeSupportSerializer {
		
	@Override
	public boolean support(Class<?> classType) {
		return classType.isArray() ? false : super.support(classType);
	}
	
	@Override
	public <T> byte[] serialize(T source) throws SerializerException {
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) source.getClass();
		LinkedBuffer buffer = LinkedBufferUtils.getLinkedBuffer();
		try {
			if (ClassUtils.isAssignable(Date.class, clazz)) {
				return ProtobufIOUtil.toByteArray(((Date) source).getTime(), SchemaUtils.getSchema(Long.class), buffer);
			}
			return ProtobufIOUtil.toByteArray(source, SchemaUtils.getSchema(clazz), buffer);
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
			if (ClassUtils.isAssignable(Date.class, clazz)) {
				ProtobufIOUtil.writeTo(outputStream, ((Date) source).getTime(), SchemaUtils.getSchema(Long.class), buffer);
			} else {
				ProtobufIOUtil.writeTo(outputStream, source, SchemaUtils.getSchema(clazz), buffer);
			}
		} catch (Exception e) {
			throw new SerializerException(e.getMessage(), e);
		} finally {
			buffer.clear();
		}
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws DeserializerException {
		try {
			if (ClassUtils.isAssignable(Date.class, clazz)) {
				Long result = Long.MAX_VALUE;
				ProtobufIOUtil.mergeFrom(bytes, result, SchemaUtils.getSchema(Long.class));
				return clazz.cast(new Date(result));
			}
			
			T result = null;
			if (ClassUtils.isAssignable(Number.class, clazz)) {
				result = clazz.cast(0);
			} else {
				result = BeanUtils.instantiate(clazz);
			}
			ProtobufIOUtil.mergeFrom(bytes, result, SchemaUtils.getSchema(clazz));
			return result;
		} catch (Exception e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}

	@Override
	public <T> T deserialize(InputStream inputStream, Class<T> clazz) throws DeserializerException {
		try {
			if (Date.class.isAssignableFrom(clazz)) {
				Long result = Long.MAX_VALUE;
				ProtobufIOUtil.mergeDelimitedFrom(inputStream, result, SchemaUtils.getSchema(Long.class));
				return clazz.cast(new Date(result));
			}
			
			T result = null;
			if (ClassUtils.isAssignable(Number.class, clazz)) {
				result = clazz.cast(0);
			} else {
				result = BeanUtils.instantiate(clazz);
			}
			ProtobufIOUtil.mergeDelimitedFrom(inputStream, result, SchemaUtils.getSchema(clazz));
			return result;
		} catch (Exception e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}
}
