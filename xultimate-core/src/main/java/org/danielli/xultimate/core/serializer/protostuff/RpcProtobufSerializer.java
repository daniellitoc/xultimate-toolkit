package org.danielli.xultimate.core.serializer.protostuff;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang3.mutable.MutableObject;
import org.danielli.xultimate.core.serializer.DeserializerException;
import org.danielli.xultimate.core.serializer.RpcSerializer;
import org.danielli.xultimate.core.serializer.SerializerException;
import org.danielli.xultimate.core.serializer.protostuff.util.LinkedBufferUtils;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * Protobuf序列化和反序列化处理类。性能比使用手动IO好。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * 
 */
public class RpcProtobufSerializer extends RpcSerializer {
	
	@SuppressWarnings("rawtypes")
	private final Schema<MutableObject> schema = RuntimeSchema.createFrom(MutableObject.class); 
	
	protected int bufferSize = 10 * 1024;
	
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	
	@Override
	public <T> byte[] serialize(T source) throws SerializerException {
		MutableObject<T> holder = new MutableObject<T>(source);
		LinkedBuffer buffer = LinkedBufferUtils.getCurrentLinkedBuffer(bufferSize);
		try {
			return ProtobufIOUtil.toByteArray(holder, schema, buffer);
		} catch (Exception e) {
			throw new SerializerException(e.getMessage(), e);
		} finally {
			buffer.clear();
		}
		
	}
	
	@Override
	public <T> void serialize(T source, OutputStream outputStream) throws SerializerException {
		MutableObject<T> holder = new MutableObject<T>(source);
		LinkedBuffer buffer = LinkedBufferUtils.getCurrentLinkedBuffer(bufferSize);
		try {
			ProtobufIOUtil.writeDelimitedTo(outputStream, holder, schema, buffer);
		} catch (Exception e) {
			throw new SerializerException(e.getMessage(), e);
		} finally {
			buffer.clear();
		}
	}
	
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws DeserializerException {
		MutableObject<T> holder = new MutableObject<T>();
		try {
			ProtobufIOUtil.mergeFrom(bytes, holder, schema);
		} catch (Exception e) {
			throw new DeserializerException(e.getMessage(), e);
		}
		return holder.getValue();
	}

	@Override
	public <T> T deserialize(InputStream inputStream, Class<T> clazz) throws DeserializerException {
		MutableObject<T> holder = new MutableObject<T>();
		LinkedBuffer buffer = LinkedBufferUtils.getCurrentLinkedBuffer(bufferSize);
		try {
			ProtobufIOUtil.mergeDelimitedFrom(inputStream, holder, schema, buffer);
		} catch (Exception e) {
			throw new DeserializerException(e.getMessage(), e);
		} finally {
			buffer.clear();
		}
		return holder.getValue();
	}
}