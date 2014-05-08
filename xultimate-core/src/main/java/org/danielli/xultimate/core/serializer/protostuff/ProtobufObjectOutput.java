package org.danielli.xultimate.core.serializer.protostuff;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.lang3.mutable.MutableObject;
import org.danielli.xultimate.core.io.AbstractObjectOutput;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class ProtobufObjectOutput extends AbstractObjectOutput {

	@SuppressWarnings("rawtypes")
	public static final Schema<MutableObject> SCHEMA = RuntimeSchema.createFrom(MutableObject.class); 
	
	private final LinkedBuffer buffer;

	public ProtobufObjectOutput (int bufferSize) throws IOException {
		super(bufferSize);
		this.buffer = LinkedBuffer.allocate(bufferSize);
	}

	public ProtobufObjectOutput (int bufferSize, int maxBufferSize) throws IOException {
		super(bufferSize, maxBufferSize);
		this.buffer = LinkedBuffer.allocate(maxBufferSize);
	}

	public ProtobufObjectOutput (byte[] buffer) throws IOException {
		super(buffer);
		this.buffer = LinkedBuffer.allocate(buffer.length);
	}

	public ProtobufObjectOutput (byte[] buffer, int maxBufferSize) throws IOException {
		super(buffer, maxBufferSize);
		this.buffer = LinkedBuffer.allocate(maxBufferSize);
	}

	public ProtobufObjectOutput (OutputStream outputStream) throws IOException {
		super(outputStream);
		this.buffer = LinkedBuffer.allocate(256);
	}

	public ProtobufObjectOutput (OutputStream outputStream, int bufferSize) throws IOException {
		super(outputStream, bufferSize);
		this.buffer = LinkedBuffer.allocate(bufferSize);
	}

	
	@Override
	public void writeObject(Object obj) throws IOException {
		if (obj == null) {
			writeByte(0);
		} else {
			writeByte(1);
			MutableObject<Object> holder = new MutableObject<Object>(obj);
			try {
				ProtobufIOUtil.writeDelimitedTo(this, holder, SCHEMA, buffer);
			} finally {
				buffer.clear();
			}
		}
		
	}
}
