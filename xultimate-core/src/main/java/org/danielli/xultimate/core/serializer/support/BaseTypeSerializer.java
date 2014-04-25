package org.danielli.xultimate.core.serializer.support;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.danielli.xultimate.core.serializer.Serializer;
import org.danielli.xultimate.core.serializer.SerializerException;
import org.danielli.xultimate.core.serializer.java.util.SerializerUtils;
import org.danielli.xultimate.util.StringUtils;

public class BaseTypeSerializer implements Serializer {

	public static final int SERIALIZED = 1;
	public static final int COMPRESSED = 2;

	public static final int SPECIAL_MASK = 0xffff << 1;
	public static final int SPECIAL_STRING = 0;
	public static final int SPECIAL_LONG = (1 << 2);
	public static final int SPECIAL_INT = (2 << 2);
	public static final int SPECIAL_BOOLEAN = (3 << 2);
	public static final int SPECIAL_DATE = (4 << 2);
	public static final int SPECIAL_DOUBLE = (5 << 2);
	public static final int SPECIAL_CHAR = (6 << 2);
	public static final int SPECIAL_FLOAT = (7 << 2);
	public static final int SPECIAL_SHORT = (8 << 2);
	public static final int SPECIAL_BYTE = (9 << 2);
	public static final int SPECIAL_BYTEARRAY = (10 << 2);
	
	protected boolean packZerosWithoutOutputStream = true;
	
	protected boolean checkKnownType = true;
	
	protected boolean exportKnownTypeToOutputStream = false;
	
	protected Serializer serializer;
	
	public void setPackZerosWithoutOutputStream(boolean packZerosWithoutOutputStream) {
		this.packZerosWithoutOutputStream = packZerosWithoutOutputStream;
	}
	
	public void setCheckKnownType(boolean checkKnownType) {
		this.checkKnownType = checkKnownType;
	}
	
	public void setExportKnownTypeToOutputStream(boolean exportKnownTypeToOutputStream) {
		this.exportKnownTypeToOutputStream = exportKnownTypeToOutputStream;
	}
	
	public void setSerializer(Serializer serializer) {
		this.serializer = serializer;
	}

	public byte[] serializeBoolean(boolean source) throws SerializerException {
		return SerializerUtils.encodeBoolean(source);
	}

	public void serializeBoolean(boolean source, OutputStream outputStream) throws SerializerException {
		try {
			outputStream.write(SerializerUtils.encodeBoolean(source));
		} catch (IOException e) {
			throw new SerializerException(e.getMessage(), e);
		}
	}
	
	public byte[] serializeByte(byte source) throws SerializerException {
		return SerializerUtils.encodeByte(source);
	}

	public void serializeByte(byte source, OutputStream outputStream) throws SerializerException {
		try {
			outputStream.write(SerializerUtils.encodeByte(source));
		} catch (IOException e) {
			throw new SerializerException(e.getMessage(), e);
		}
	}
	
	public byte[] serializeDouble(double source) throws SerializerException {
		return SerializerUtils.encodeLong(Double.doubleToLongBits(source), packZerosWithoutOutputStream);
	}

	public void serializeDouble(double source, OutputStream outputStream) throws SerializerException {
		try {
			outputStream.write(SerializerUtils.encodeLong(Double.doubleToLongBits(source), false));
		} catch (IOException e) {
			throw new SerializerException(e.getMessage(), e);
		}
	}
	
	public byte[] serializeFloat(float source) throws SerializerException {
		return SerializerUtils.encodeInt(Float.floatToRawIntBits(source), packZerosWithoutOutputStream);
	}

	public void serializeFloat(float source, OutputStream outputStream) throws SerializerException {
		try {
			outputStream.write(SerializerUtils.encodeInt(Float.floatToRawIntBits(source), false));
		} catch (IOException e) {
			throw new SerializerException(e.getMessage(), e);
		}
	}
	
	public <T> byte[] serializeInt(int source) throws SerializerException {
		return SerializerUtils.encodeInt(source, packZerosWithoutOutputStream);
	}

	public void serializeInt(int source, OutputStream outputStream) throws SerializerException {
		try {
			outputStream.write(SerializerUtils.encodeInt(source, false));
		} catch (IOException e) {
			throw new SerializerException(e.getMessage(), e);
		}
	}
	
	public <T> byte[] serializeChar(char source) throws SerializerException {
		return SerializerUtils.encodeInt(source, packZerosWithoutOutputStream);
	}

	public void serializeChar(char source, OutputStream outputStream) throws SerializerException {
		try {
			outputStream.write(SerializerUtils.encodeInt(source, false));
		} catch (IOException e) {
			throw new SerializerException(e.getMessage(), e);
		}
	}
	
	public byte[] serializeLong(long source) throws SerializerException {
		return SerializerUtils.encodeLong(source, packZerosWithoutOutputStream);
	}

	public void serializeLong(long source, OutputStream outputStream) throws SerializerException {
		try {
			outputStream.write(SerializerUtils.encodeLong(source, false));
		} catch (IOException e) {
			throw new SerializerException(e.getMessage(), e);
		}
	}
	
	public byte[] serializeShort(short source) throws SerializerException {
		return SerializerUtils.encodeShort(source, packZerosWithoutOutputStream);
	}

	public void serializeShort(short source, OutputStream outputStream) throws SerializerException {
		try {
			outputStream.write(SerializerUtils.encodeShort(source, false));
		} catch (IOException e) {
			throw new SerializerException(e.getMessage(), e);
		}
	}
	
	public byte[] serializeString(String source) throws SerializerException {
		return StringUtils.getBytesUtf8(source);
	}

	public void serializeString(String source, OutputStream outputStream) throws SerializerException {
		try {
			if (source == null) {
				outputStream.write(SerializerUtils.encodeInt(-1, false));
			} else {
				byte[] result = StringUtils.getBytesUtf8(source);
				outputStream.write(SerializerUtils.encodeInt(result.length, false));
				outputStream.write(result);
			}
		} catch (IOException e) {
			throw new SerializerException(e.getMessage(), e);
		}
	}
	
	public void serializeBytes(byte[] source, OutputStream outputStream) throws SerializerException {
		try {
			if (source == null) {
				outputStream.write(SerializerUtils.encodeInt(-1, false));
			} else {
				outputStream.write(SerializerUtils.encodeInt(source.length, false));
				outputStream.write(source);
			}
		} catch (IOException e) {
			throw new SerializerException(e.getMessage(), e);
		}
	}
	
	public void serializeBytes(byte[] source, int offset, int length, OutputStream outputStream) throws SerializerException {
		try {
			if (source == null) {
				outputStream.write(SerializerUtils.encodeInt(-1, false));
			} else {
				outputStream.write(SerializerUtils.encodeInt(length, false));
				outputStream.write(source, offset, length);
			}
		} catch (IOException e) {
			throw new SerializerException(e.getMessage(), e);
		}
	}
	
	@Override
	public <T> byte[] serialize(T source) throws SerializerException {
		if (checkKnownType && source != null) {
			if (source instanceof String) {
				return serializeString((String) source);
			} else if (source instanceof Long) {
				return serializeLong((Long) source);
			} else if (source instanceof Integer) {
				return serializeInt((Integer) source);
			} else if (source instanceof Boolean) {
				return serializeBoolean((Boolean) source);
			} else if (source instanceof Date) {
				return serializeLong(((Date) source).getTime());
			} else if (source instanceof Double) {
				return serializeDouble((Double) source);
			} else if (source instanceof Character) {
				return serializeChar((Character) source);
			} else if (source instanceof Float) {
				return serializeFloat((Float) source);
			} else if (source instanceof Short) {
				return serializeShort((Short) source);
			} else if (source instanceof Byte) {
				return serializeByte((Byte) source);
			} else if (source instanceof byte[]) {
				return (byte[]) source;
			} else {
				byte[] result = serializeExternal(source);
				if (result != null) {
					return result;
				}
			}
		}
		return serializer.serialize(source);
	}
	
	public <T> byte[] serializeExternal(T source) throws SerializerException {
		return null;
	}

	@Override
	public <T> void serialize(T source, OutputStream outputStream) throws SerializerException {
		if ((checkKnownType || exportKnownTypeToOutputStream) && source != null) {
			if (source instanceof String) {
				if (exportKnownTypeToOutputStream) {
					serializeInt(SPECIAL_STRING, outputStream);
				}
				serializeString((String) source, outputStream);
				return;
			} else if (source instanceof Long) {
				if (exportKnownTypeToOutputStream) {
					serializeInt(SPECIAL_LONG, outputStream);
				}
				serializeLong((Long) source, outputStream);
				return;
			} else if (source instanceof Integer) {
				if (exportKnownTypeToOutputStream) {
					serializeInt(SPECIAL_INT, outputStream);
				}
				serializeInt((Integer) source, outputStream);
				return;
			} else if (source instanceof Boolean) {
				if (exportKnownTypeToOutputStream) {
					serializeInt(SPECIAL_BOOLEAN, outputStream);
				}
				serializeBoolean((Boolean) source, outputStream);
				return;
			} else if (source instanceof Date) {
				if (exportKnownTypeToOutputStream) {
					serializeInt(SPECIAL_DATE, outputStream);
				}
				serializeLong(((Date) source).getTime(), outputStream);
				return;
			} else if (source instanceof Double) {
				if (exportKnownTypeToOutputStream) {
					serializeInt(SPECIAL_DOUBLE, outputStream);
				}
				serializeDouble((Double) source, outputStream);
				return;
			} else if (source instanceof Character) {
				if (exportKnownTypeToOutputStream) {
					serializeInt(SPECIAL_CHAR, outputStream);
				}
				serializeChar((Character) source, outputStream);
				return;
			} else if (source instanceof Float) {
				if (exportKnownTypeToOutputStream) {
					serializeInt(SPECIAL_FLOAT, outputStream);
				}
				serializeFloat((Float) source, outputStream);
				return;
			} else if (source instanceof Short) {
				if (exportKnownTypeToOutputStream) {
					serializeInt(SPECIAL_SHORT, outputStream);
				}
				serializeShort((Short) source, outputStream);
				return;
			} else if (source instanceof Byte) {
				if (exportKnownTypeToOutputStream) {
					serializeInt(SPECIAL_BYTE, outputStream);
				}
				serializeByte((Byte) source, outputStream);
				return;
			} else if (source instanceof byte[]) {
				if (exportKnownTypeToOutputStream) {
					serializeInt(SPECIAL_BYTEARRAY, outputStream);
				}
				serializeBytes((byte[]) source, outputStream);
				return;
			} else {
				if (serializeExternal(source, outputStream)) {
					return;
				}
			}
		}
		if (exportKnownTypeToOutputStream) {
			serializeInt(SERIALIZED, outputStream);
		}
		serializer.serialize(source, outputStream);
	}
	
	public <T> boolean serializeExternal(T source, OutputStream outputStream) throws SerializerException {
		return false;
	}
}
