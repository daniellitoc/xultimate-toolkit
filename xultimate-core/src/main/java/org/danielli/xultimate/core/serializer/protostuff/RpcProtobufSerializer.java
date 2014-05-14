package org.danielli.xultimate.core.serializer.protostuff;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.danielli.xultimate.core.serializer.DeserializerException;
import org.danielli.xultimate.core.serializer.RpcSerializer;
import org.danielli.xultimate.core.serializer.SerializerException;
import org.danielli.xultimate.core.serializer.kryo.KryoGenerator;
import org.danielli.xultimate.core.serializer.kryo.support.ThreadLocalKryoGenerator;
import org.danielli.xultimate.core.serializer.protostuff.util.LinkedBufferUtils;
import org.joda.time.DateTime;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * Protobuf序列化和反序列化处理类，针对Date/DateTime、String、Collection、Map、Long/Double/Integer/Float/Character/Short/Byte/Boolean/byte[]特殊处理。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class RpcProtobufSerializer extends RpcSerializer {
	
//	@SuppressWarnings("rawtypes")
//	private static final Schema<MutableObject> schema = RuntimeSchema.getSchema(MutableObject.class);
	
	protected KryoGenerator kryoGenerator = ThreadLocalKryoGenerator.INSTANCE;
	
	protected int bufferSize = 256;
	
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	
	public void setKryoGenerator(KryoGenerator kryoGenerator) {
		this.kryoGenerator = kryoGenerator;
	}
	
	@Override
	public <T> byte[] serialize(T source) throws SerializerException {
		Output output = new Output(bufferSize, -1);
		serialize(source, output);
		return output.toBytes();
	}
	
	@Override
	public <T> void serialize(T source, OutputStream outputStream) throws SerializerException {
		if (!(outputStream instanceof Output)) {
			throw new SerializerException("Parameter 'outputStream' must be com.esotericsoftware.kryo.io.Output type");
		}
		serialize(source, (Output) outputStream);
	}
	
	public <T> void serialize(T source, Output output) throws SerializerException {
		try {
			if (source == null) {
				output.writeByte(0);
			} else if (source instanceof Collection || source instanceof Map) {
				output.writeByte(1);
				kryoGenerator.generate().writeClassAndObject(output, source);
			} else if (source instanceof String) {
				output.writeByte(2);
				output.writeString((String) source);
			} else if (source instanceof Long) {
				output.writeByte(3);
				output.writeLong((Long) source, true);
			} else if (source instanceof Integer) {
				output.writeByte(4);
				output.writeInt((Integer) source, true);
			} else if (source instanceof Boolean) {
				output.writeByte(5);
				output.writeBoolean((Boolean) source);
			} else if (source instanceof Date) {
				output.writeByte(8);
				output.writeLong(((Date) source).getTime(), true);
			} else if (source instanceof DateTime) {
				output.writeByte(7);
				output.writeLong(((DateTime) source).getMillis(), true);
			} else if (source instanceof Double) {
				output.writeByte(9);
				output.writeDouble((Double) source);
			} else if (source instanceof Character) {
				output.writeByte(10);
				output.writeChar((Character) source);
			} else if (source instanceof Float) {
				output.writeByte(11);
				output.writeFloat((Float) source);
			} else if (source instanceof Short) {
				output.writeByte(12);
				output.writeShort((Short) source);
			} else if (source instanceof Byte) {
				output.writeByte(13);
				output.writeByte((Byte) source);
			} else if (source instanceof byte[]) {
				output.writeByte(14);
				byte[] result = (byte[]) source;
				output.writeInt(result.length, true);
				output.writeBytes(result);
			} else {
				output.writeByte(6);
//				MutableObject<T> holder = new MutableObject<T>(source);
				LinkedBuffer buffer = LinkedBufferUtils.getCurrentLinkedBuffer(bufferSize);
				@SuppressWarnings("unchecked")
				Class<T> clazz = (Class<T>) source.getClass();
				kryoGenerator.generate().writeClass(output, clazz);
				try {
//					ProtobufIOUtil.writeDelimitedTo(output, holder, schema, buffer);
					ProtobufIOUtil.writeDelimitedTo(output, source, RuntimeSchema.getSchema(clazz), buffer);
				} catch (Exception e) {
					throw new SerializerException(e.getMessage(), e);
				} finally {
					buffer.clear();
				}
			}
		} catch (Exception e) {
			throw new SerializerException(e.getMessage(), e);
		}
	}
	
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws DeserializerException {
		Input input = new Input(bytes);
		return deserialize(input, clazz);
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
			switch (input.readByte()) {
			case 0:
				return null;
			case 1:
				return (T) kryoGenerator.generate().readClassAndObject(input);
			case 2:
				return (T) input.readString();
			case 3:
				return (T) (Long) input.readLong(true);
			case 4:
				return (T) (Integer) input.readInt(true);
			case 5:
				return (T) (Boolean) input.readBoolean();
			case 8:
				return (T) new Date(input.readLong(true));
			case 7:
				return (T) new DateTime(input.readLong(true));
			case 9:
				return (T) (Double) input.readDouble();
			case 10:
				return (T) (Character) input.readChar();
			case 11:
				return (T) (Float) input.readFloat();
			case 12:
				return (T) (Short) input.readShort();
			case 13:
				return (T) (Byte) input.readByte();
			case 14:
				int length = input.readInt(true);
				return (T) input.readBytes(length);
			default:
				Class<T> type = kryoGenerator.generate().readClass(input).getType();
				Schema<T> schema = RuntimeSchema.getSchema(type);
//				MutableObject<T> holder = new MutableObject<T>();
				try {
//					ProtobufIOUtil.mergeDelimitedFrom(input, holder, schema, buffer);
					T result = schema.newMessage();
					ProtobufIOUtil.mergeDelimitedFrom(input, result, schema);
					return result;
				} catch (Exception e) {
					throw new DeserializerException(e.getMessage(), e);
				}
//				return holder.getValue();
			}
		} catch (Exception e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}
}