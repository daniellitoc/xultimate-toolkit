package org.danielli.xultimate.core.serializer.protostuff;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.mutable.MutableObject;
import org.danielli.xultimate.core.io.AbstractObjectInput;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class ProtobufObjectInput extends AbstractObjectInput {

	@SuppressWarnings("rawtypes")
	public static final Schema<MutableObject> SCHEMA = RuntimeSchema.createFrom(MutableObject.class); 
	
	private final LinkedBuffer buffer;

	public ProtobufObjectInput (int bufferSize) {
		super(bufferSize);
		this.buffer = LinkedBuffer.allocate(bufferSize);
	}

	public ProtobufObjectInput (byte[] buffer) {
		super(buffer);
		this.buffer = LinkedBuffer.allocate(buffer.length);
	}

	public ProtobufObjectInput (byte[] buffer, int offset, int count) {
		super(buffer, offset, count);
		this.buffer = LinkedBuffer.allocate(buffer.length);
	}

	public ProtobufObjectInput (InputStream inputStream) {
		super(inputStream);
		this.buffer = LinkedBuffer.allocate(256);
	}

	public ProtobufObjectInput (InputStream inputStream, int bufferSize) {
		super(inputStream, bufferSize);
		this.buffer = LinkedBuffer.allocate(bufferSize);
	}
	
	@Override
	public Object readObject() throws IOException, ClassNotFoundException {
		byte sign = readByte();
		if (sign == 0) {
			return null;
		}
		MutableObject<Object> holder = new MutableObject<Object>();
		try {
			ProtobufIOUtil.mergeDelimitedFrom(this, holder, SCHEMA, buffer);
		} finally {
			buffer.clear();
		}
		return holder.getValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException {
		return (T) readObject();
	}

}
