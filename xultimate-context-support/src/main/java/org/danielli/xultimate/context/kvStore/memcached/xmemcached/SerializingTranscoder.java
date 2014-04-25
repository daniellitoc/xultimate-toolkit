package org.danielli.xultimate.context.kvStore.memcached.xmemcached;

import java.util.Date;

import net.rubyeye.xmemcached.transcoders.CachedData;
import net.rubyeye.xmemcached.transcoders.Transcoder;

import org.danielli.xultimate.core.serializer.support.BaseTypeDeserializer;
import org.danielli.xultimate.core.serializer.support.BaseTypeSerializer;

/**
 * 替代其默认的序列化。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 *
 */
public class SerializingTranscoder extends BaseSerializingTranscoder implements Transcoder<Object> {

	private final int maxSize;

	public final int getMaxSize() {
		return this.maxSize;
	}

	// General flags
	public static final int SERIALIZED = 1;
	public static final int COMPRESSED = 2;

	// Special flags for specially handled types.
	public static final int SPECIAL_MASK =  ~2;
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
	
	/**
	 * Get a serializing transcoder with the default max data size.
	 */
	public SerializingTranscoder() {
		this(CachedData.MAX_SIZE);
	}

	/**
	 * Get a serializing transcoder that specifies the max data size.
	 */
	public SerializingTranscoder(int max) {
		this.maxSize = max;
	}
	
	@Override
	public boolean isPrimitiveAsString() {
		return false;
	}
	
	@Override
	public boolean isPackZeros() {
		return true;
	}
	
	protected BaseTypeSerializer baseTypeSerializer;
	
	protected BaseTypeDeserializer baseTypeDeserializer;
	
	@Override
	public CachedData encode(Object o) {
		byte[] b = null;
		int flags = SPECIAL_STRING;
		if (o instanceof String) {
			b = baseTypeSerializer.serializeString((String) o);
		} else if (o instanceof Long) {
			b = baseTypeSerializer.serializeLong((Long) o);
			flags |= SPECIAL_LONG;
		} else if (o instanceof Integer) {
			b = baseTypeSerializer.serializeInt((Integer) o);
			flags |= SPECIAL_INT;
		} else if (o instanceof Boolean) {
			b = baseTypeSerializer.serializeBoolean((Boolean) o);
			flags |= SPECIAL_BOOLEAN;
		} else if (o instanceof Date) {
			b = baseTypeSerializer.serializeLong(((Date) o).getTime());
			flags |= SPECIAL_DATE;
		} else if (o instanceof Double) {
			b = baseTypeSerializer.serializeDouble((Double) o);
			flags |= SPECIAL_DOUBLE;
		} else if (o instanceof Character) {
			b = baseTypeSerializer.serializeChar((Character) o);
			flags |= SPECIAL_CHAR;
		} else if (o instanceof Float) {
			b = baseTypeSerializer.serializeFloat((Byte) o);
			flags |= SPECIAL_FLOAT;
		} else if (o instanceof Short) {
			b = baseTypeSerializer.serializeShort((Short) o);
			flags |= SPECIAL_SHORT;
		} else if (o instanceof Byte) {
			b = baseTypeSerializer.serializeByte((Byte) o);
			flags |= SPECIAL_BYTE;
		} else if (o instanceof byte[]) {
			b = (byte[]) o;
			flags |= SPECIAL_BYTEARRAY;
		} else {
			b = baseTypeSerializer.serialize(o);
			flags |= SERIALIZED;
		}
		assert b != null;
		if (b.length > this.compressionThreshold) {
			byte[] compressed = compress(b);
			if (compressed.length < b.length) {
				if (log.isDebugEnabled()) {
					log.debug("Compressed " + o.getClass().getName() + " from "
							+ b.length + " to " + compressed.length);
				}
				b = compressed;
				flags |= COMPRESSED;
			} else {
				if (log.isDebugEnabled()) {
					log.debug("Compression increased the size of "
							+ o.getClass().getName() + " from " + b.length
							+ " to " + compressed.length);
				}
			}
		}
		return new CachedData(flags, b, this.maxSize, -1);
	}

	@Override
	public Object decode(CachedData d) {
		byte[] data = d.getData();

		int flags = d.getFlag();
		if ((flags & COMPRESSED) != 0) {
			data = decompress(d.getData());
		}
		flags &= SPECIAL_MASK;
		return decode0(d,data, flags);
	}
	
	protected final Object decode0(CachedData cachedData,byte[] data, int flags) {
		Object rv = null;
		if ((flags & SERIALIZED) != 0 && data != null) {
			rv = baseTypeDeserializer.deserialize(data, Object.class);
		} else {
			if (flags != SPECIAL_STRING && data != null) {
				switch (flags) {
				case SPECIAL_LONG:
					rv = baseTypeDeserializer.deserializeLong(data);
					break;
				case SPECIAL_INT:
					rv = baseTypeDeserializer.deserializeInt(data);
					break;
				case SPECIAL_BOOLEAN:
					rv = baseTypeDeserializer.deserializeBoolean(data);
					break;
				case SPECIAL_DATE:
					rv = new Date(baseTypeDeserializer.deserializeLong(data));
					break;
				case SPECIAL_DOUBLE:
					rv = baseTypeDeserializer.deserializeDouble(data);
					break;			
				case SPECIAL_CHAR:
					rv = baseTypeDeserializer.deserializeChar(data);
					break;	
				case SPECIAL_FLOAT:
					rv = baseTypeDeserializer.deserializeFloat(data);
					break;
				case SPECIAL_SHORT:
					rv = baseTypeDeserializer.deserializeShort(data);
					break;
				case SPECIAL_BYTE:
					rv = baseTypeDeserializer.deserializeByte(data);
					break;
				case SPECIAL_BYTEARRAY:
					rv = data;
					break;
				default:
					log.warn(String.format("Undecodeable with flags %x", flags));
				}
			} else {
				rv = baseTypeDeserializer.deserializeString(data);
			}
		}
		return rv;
	}

	@Override
	public void setPrimitiveAsString(boolean primitiveAsString) { }

	@Override
	public void setPackZeros(boolean packZeros) { }

	public void setBaseTypeSerializer(BaseTypeSerializer baseTypeSerializer) {
		this.baseTypeSerializer = baseTypeSerializer;
	}

	public void setBaseTypeDeserializer(BaseTypeDeserializer baseTypeDeserializer) {
		this.baseTypeDeserializer = baseTypeDeserializer;
	}
}
