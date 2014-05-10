package org.danielli.xultimate.core.io.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.danielli.xultimate.core.io.AbstractObjectInput;
import org.joda.time.DateTime;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.esotericsoftware.kryo.Kryo;

/**
 * Protobuf序列化机制的对象输入流，针对Date/DateTime、String、Collection、Map、Long/Double/Integer/Float/Character/Short/Byte/Boolean/byte[]特殊处理。
 * 
 * @author Daniel Li
 * @since 10 May 2014
 */
public class RpcProtobufObjectInput extends AbstractObjectInput {

//	@SuppressWarnings("rawtypes")
//	public static final Schema<MutableObject> SCHEMA = RuntimeSchema.getSchema(MutableObject.class);
	
	private final LinkedBuffer buffer;
	
	private final Kryo kryo;

	public RpcProtobufObjectInput (int bufferSize, LinkedBuffer linkedBuffer, Kryo kryo) {
		super(bufferSize);
		this.buffer = linkedBuffer;
		this.kryo = kryo;
	}

	public RpcProtobufObjectInput (byte[] buffer, LinkedBuffer linkedBuffer, Kryo kryo) {
		super(buffer);
		this.buffer = linkedBuffer;
		this.kryo = kryo;
	}

	public RpcProtobufObjectInput (byte[] buffer, int offset, int count, LinkedBuffer linkedBuffer, Kryo kryo) {
		super(buffer, offset, count);
		this.buffer = linkedBuffer;
		this.kryo = kryo;
	}

	public RpcProtobufObjectInput (InputStream inputStream, LinkedBuffer linkedBuffer, Kryo kryo) {
		super(inputStream);
		this.buffer = linkedBuffer;
		this.kryo = kryo;
	}

	public RpcProtobufObjectInput (InputStream inputStream, int bufferSize, LinkedBuffer linkedBuffer, Kryo kryo) {
		super(inputStream, bufferSize);
		this.buffer = linkedBuffer;
		this.kryo = kryo;
	}
	
	@Override
	public Object readObject() throws IOException, ClassNotFoundException {
		switch (readByte()) {
		case 0:
			return null;
		case 1:
			return kryo.readClassAndObject(this);
		case 2:
			return readString();
		case 3:
			return readLong(true);
		case 4:
			return readInt(true);
		case 5:
			return readBoolean();
		case 8:
			return new Date(readLong(true));
		case 7:
			return new DateTime(readLong(true));
		case 9:
			return readDouble();
		case 10:
			return readChar();
		case 11:
			return readFloat();
		case 12:
			return readShort();
		case 13:
			return readByte();
		case 14:
			int length = readInt(true);
			return readBytes(length);
		default:
			@SuppressWarnings("unchecked")
			Class<Object> type = kryo.readClass(this).getType();
			Schema<Object> schema = RuntimeSchema.getSchema(type);
//			MutableObject<Object> holder = new MutableObject<Object>();
			try {
//				ProtobufIOUtil.mergeDelimitedFrom(this, holder, SCHEMA, buffer);
				Object result = schema.newMessage();
				ProtobufIOUtil.mergeDelimitedFrom(this, result, schema, buffer);
				return result;
			} finally {
				buffer.clear();
			}
//			return holder.getValue();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException {
		return (T) readObject();
	}

}
