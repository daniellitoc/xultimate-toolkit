package org.danielli.xultimate.core.serializer.support;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.danielli.xultimate.core.serializer.Deserializer;
import org.danielli.xultimate.core.serializer.DeserializerException;
import org.danielli.xultimate.core.serializer.java.util.SerializerUtils;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.reflect.ClassUtils;

public class BaseTypeDeserializer implements Deserializer {
	
	protected boolean checkKnownType = true;
	
	protected boolean importKnownTypeFromInputStream = false;
	
	protected Deserializer deserializer;
	
	public void setCheckKnownType(boolean checkKnownType) {
		this.checkKnownType = checkKnownType;
	}
	
	public void setImportKnownTypeFromInputStream(boolean importKnownTypeFromInputStream) {
		this.importKnownTypeFromInputStream = importKnownTypeFromInputStream;
	}
	
	public void setDeserializer(Deserializer deserializer) {
		this.deserializer = deserializer;
	}
	
	public boolean deserializeBoolean(byte[] bytes) throws DeserializerException {
		return SerializerUtils.decodeBoolean(bytes);
	}

	public boolean deserializeBoolean(InputStream inputStream) throws DeserializerException {
		try {
			byte[] result = new byte[SerializerUtils.BOOLEAN_BYTE_SIZE];
			inputStream.read(result);
			return SerializerUtils.decodeBoolean(result);
		} catch (IOException e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}
	
	public byte deserializeByte(byte[] bytes) throws DeserializerException {
		return SerializerUtils.decodeByte(bytes);
	}

	public byte deserializeByte(InputStream inputStream) throws DeserializerException {
		try {
			byte[] result = new byte[SerializerUtils.BYTE_BYTE_SIZE];
			inputStream.read(result);
			return SerializerUtils.decodeByte(result);
		} catch (IOException e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}
	
	public double deserializeDouble(byte[] bytes) throws DeserializerException {
		return Double.longBitsToDouble(SerializerUtils.decodeLong(bytes));
	}

	public double deserializeDouble(InputStream inputStream) throws DeserializerException {
		try {
			byte[] result = new byte[SerializerUtils.LONG_BYTE_SIZE];
			inputStream.read(result);
			return Double.longBitsToDouble(SerializerUtils.decodeLong(result));
		} catch (IOException e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}
	
	public float deserializeFloat(byte[] bytes) throws DeserializerException {
		return Float.intBitsToFloat(SerializerUtils.decodeInt(bytes));
	}

	public float deserializeFloat(InputStream inputStream) throws DeserializerException {
		try {
			byte[] result = new byte[SerializerUtils.INT_BYTE_SIZE];
			inputStream.read(result);
			return Float.intBitsToFloat(SerializerUtils.decodeInt(result));
		} catch (IOException e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}
	
	public int deserializeInt(byte[] bytes) throws DeserializerException {
		return SerializerUtils.decodeInt(bytes);
	}

	public int deserializeInt(InputStream inputStream) throws DeserializerException {
		try {
			byte[] result = new byte[SerializerUtils.INT_BYTE_SIZE];
			inputStream.read(result);
			return SerializerUtils.decodeInt(result);
		} catch (IOException e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}
	
	public char deserializeChar(byte[] bytes) throws DeserializerException {
		return (char) SerializerUtils.decodeInt(bytes);
	}

	public char deserializeChar(InputStream inputStream) throws DeserializerException {
		try {
			byte[] result = new byte[SerializerUtils.INT_BYTE_SIZE];
			inputStream.read(result);
			return (char) SerializerUtils.decodeInt(result);
		} catch (IOException e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}
	
	public long deserializeLong(byte[] bytes) throws DeserializerException {
		return SerializerUtils.decodeLong(bytes);
	}

	public long deserializeLong(InputStream inputStream) throws DeserializerException {
		try {
			byte[] result = new byte[SerializerUtils.LONG_BYTE_SIZE];
			inputStream.read(result);
			return SerializerUtils.decodeLong(result);
		} catch (IOException e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}
	
	public short deserializeShort(byte[] bytes) throws DeserializerException {
		return (short) SerializerUtils.decodeShort(bytes);
	}

	public short deserializeShort(InputStream inputStream) throws DeserializerException {
		try {
			byte[] result = new byte[SerializerUtils.SHORT_BYTE_SIZE];
			inputStream.read(result);
			return (short) SerializerUtils.decodeShort(result);
		} catch (IOException e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}
	
	public String deserializeString(byte[] bytes) throws DeserializerException {
		return StringUtils.newStringUtf8(bytes);
	}

	public String deserializeString(InputStream inputStream) throws DeserializerException {
		try {
			byte[] result = new byte[SerializerUtils.INT_BYTE_SIZE];
			inputStream.read(result);
			int length = SerializerUtils.decodeInt(result);
			
			if (length == -1) {
				return null;
			}
			result = new byte[length];
			inputStream.read(result);
			return StringUtils.newStringUtf8(result);
		} catch (IOException e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}
	
	public byte[] deserializeBytes(InputStream inputStream) throws DeserializerException {
		try {
			byte[] result = new byte[SerializerUtils.INT_BYTE_SIZE];
			inputStream.read(result);
			int length = SerializerUtils.decodeInt(result);
			
			if (length == -1) {
				return null;
			}
			if (length == 0) {
				return new byte[0];
			}
			result = new byte[length];
			inputStream.read(result);
			return result;
		} catch (IOException e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}
	
	@Override
	public <T> T deserialize(byte[] bytes, Class<T> clazz) throws DeserializerException {
		if (checkKnownType && bytes != null) {
			if (ClassUtils.isAssignable(String.class, clazz)) {
				return clazz.cast(deserializeString(bytes));
			} else if (ClassUtils.isAssignable(Long.class, clazz)) {
				return clazz.cast(deserializeLong(bytes));
			} else if (ClassUtils.isAssignable(Integer.class, clazz)) {
				return clazz.cast(deserializeInt(bytes));
			} else if (ClassUtils.isAssignable(Boolean.class, clazz)) {
				return clazz.cast(deserializeBoolean(bytes));
			} else if (ClassUtils.isAssignable(Date.class, clazz)) {
				return clazz.cast(new Date(deserializeLong(bytes)));
			} else if (ClassUtils.isAssignable(Double.class, clazz)) {
				return clazz.cast(deserializeDouble(bytes));
			} else if (ClassUtils.isAssignable(Character.class, clazz)) {
				return clazz.cast(deserializeChar(bytes));
			} else if (ClassUtils.isAssignable(Float.class, clazz)) {
				return clazz.cast(deserializeFloat(bytes));
			} else if (ClassUtils.isAssignable(Short.class, clazz)) {
				return clazz.cast(deserializeShort(bytes));
			} else if (ClassUtils.isAssignable(Byte.class, clazz)) {
				return clazz.cast(deserializeByte(bytes));
			} else if (ClassUtils.isAssignable(byte[].class, clazz)) {
				return clazz.cast(bytes);
			} else {
				T result = deserializeExternal(bytes);
				if (result != null) {
					return result;
				}
			}
		}
		return deserializer.deserialize(bytes, clazz);
	}
	
	public <T> T deserializeExternal(byte[] bytes) throws DeserializerException {
		return null;
	}

	@Override
	public <T> T deserialize(InputStream inputStream, Class<T> clazz) throws DeserializerException {
		if (importKnownTypeFromInputStream) {
			int type = deserializeInt(inputStream);
			switch (type){
			case BaseTypeSerializer.SERIALIZED:
				return deserializer.deserialize(inputStream, clazz);
			case BaseTypeSerializer.SPECIAL_STRING:
				return clazz.cast(deserializeString(inputStream));
			case BaseTypeSerializer.SPECIAL_LONG:
				return clazz.cast(deserializeLong(inputStream));
			case BaseTypeSerializer.SPECIAL_INT:
				return clazz.cast(deserializeInt(inputStream));
			case BaseTypeSerializer.SPECIAL_BOOLEAN:
				return clazz.cast(deserializeBoolean(inputStream));
			case BaseTypeSerializer.SPECIAL_DATE:
				return clazz.cast(new Date(deserializeLong(inputStream)));
			case BaseTypeSerializer.SPECIAL_DOUBLE:
				return clazz.cast(deserializeDouble(inputStream));
			case BaseTypeSerializer.SPECIAL_CHAR:
				return clazz.cast(deserializeChar(inputStream));
			case BaseTypeSerializer.SPECIAL_FLOAT:
				return clazz.cast(deserializeFloat(inputStream));
			case BaseTypeSerializer.SPECIAL_SHORT:
				return clazz.cast(deserializeShort(inputStream));
			case BaseTypeSerializer.SPECIAL_BYTE:
				return clazz.cast(deserializeByte(inputStream));
			case BaseTypeSerializer.SPECIAL_BYTEARRAY:
				return clazz.cast(deserializeBytes(inputStream));
			default:
				T result = deserializeExternal(inputStream, clazz);
				if (result != null) {
					return result;
				}
			}
		} else if (checkKnownType) {
			if (ClassUtils.isAssignable(String.class, clazz)) {
				return clazz.cast(deserializeString(inputStream));
			} else if (ClassUtils.isAssignable(Long.class, clazz)) {
				return clazz.cast(deserializeLong(inputStream));
			} else if (ClassUtils.isAssignable(Integer.class, clazz)) {
				return clazz.cast(deserializeInt(inputStream));
			} else if (ClassUtils.isAssignable(Boolean.class, clazz)) {
				return clazz.cast(deserializeBoolean(inputStream));
			} else if (ClassUtils.isAssignable(Date.class, clazz)) {
				return clazz.cast(new Date(deserializeLong(inputStream)));
			} else if (ClassUtils.isAssignable(Double.class, clazz)) {
				return clazz.cast(deserializeDouble(inputStream));
			} else if (ClassUtils.isAssignable(Character.class, clazz)) {
				return clazz.cast(deserializeChar(inputStream));
			} else if (ClassUtils.isAssignable(Float.class, clazz)) {
				return clazz.cast(deserializeFloat(inputStream));
			} else if (ClassUtils.isAssignable(Short.class, clazz)) {
				return clazz.cast(deserializeShort(inputStream));
			} else if (ClassUtils.isAssignable(Byte.class, clazz)) {
				return clazz.cast(deserializeByte(inputStream));
			} else if (ClassUtils.isAssignable(byte[].class, clazz)) {
				return clazz.cast(deserializeBytes(inputStream));
			} else {
				T result = deserializeExternal(inputStream, clazz);
				if (result != null) {
					return result;
				}
			}
		}
		return deserializer.deserialize(inputStream, clazz);
	}
	
	public <T> T deserializeExternal(InputStream inputStream, Class<T> clazz) throws DeserializerException {
		return null;
	}
}
