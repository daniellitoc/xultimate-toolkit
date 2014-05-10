package org.danielli.xultimate.core.serializer.protostuff;

import java.io.InputStream;
import java.io.OutputStream;

import org.danielli.xultimate.core.serializer.AbstractClassTypeSupporterSerializer;
import org.danielli.xultimate.core.serializer.DeserializerException;
import org.danielli.xultimate.core.serializer.SerializerException;
import org.danielli.xultimate.core.serializer.protostuff.support.ProtobufClassTypeNotSupporter;
import org.danielli.xultimate.core.serializer.protostuff.util.LinkedBufferUtils;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * Protobuf主序列化和反序列化处理类。性能比手动优化差，和手动正常操作的性能差一点。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @deprecated 暂时无用。
 */
public class LocalProtobufSerializer extends AbstractClassTypeSupporterSerializer {
	
	protected int bufferSize = 256;
	
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	
	public LocalProtobufSerializer() {
		this.classTypeSupporter = ProtobufClassTypeNotSupporter.INSTANCE;
	}
	
	@Override
	public <T> byte[] serialize(T source) throws SerializerException {
		LinkedBuffer buffer = LinkedBufferUtils.getCurrentLinkedBuffer(bufferSize);
		try {
			@SuppressWarnings("unchecked")
			Class<T> clazz = (Class<T>) source.getClass();
			return ProtobufIOUtil.toByteArray(source, RuntimeSchema.getSchema(clazz), buffer);
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
			ProtobufIOUtil.writeDelimitedTo(outputStream, source, RuntimeSchema.getSchema(clazz), buffer);
		} catch (Exception e) {
			throw new SerializerException(e.getMessage(), e);
		} finally {
			buffer.clear();
		}
	}

	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws DeserializerException {
		try {
			Schema<T> schema = RuntimeSchema.getSchema(clazz);
			T result = schema.newMessage();
			ProtobufIOUtil.mergeFrom(bytes, result, schema);
			return result;
		} catch (Exception e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}

	@Override
	public <T> T deserialize(InputStream inputStream, Class<T> clazz) throws DeserializerException {
		try {
			Schema<T> schema = RuntimeSchema.getSchema(clazz);
			T result = schema.newMessage();
			ProtobufIOUtil.mergeDelimitedFrom(inputStream, result, schema);
			return result;
		} catch (Exception e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}
}
