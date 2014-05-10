package org.danielli.xultimate.context.kvStore.memcached.xmemcached;

import java.util.Date;

import net.rubyeye.xmemcached.transcoders.CachedData;
import net.rubyeye.xmemcached.transcoders.CompressionMode;
import net.rubyeye.xmemcached.transcoders.Transcoder;

import org.danielli.xultimate.core.serializer.RpcSerializer;
import org.danielli.xultimate.core.serializer.java.util.SerializerUtils;
import org.danielli.xultimate.util.StringUtils;
import org.joda.time.DateTime;

/**
 * 替代其默认的序列化。
 * 
 * @author Daniel Li
 * @since 19 Jun 2013
 */
public class SerializingTranscoder extends BaseSerializingTranscoder implements Transcoder<Object> {

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
	public static final int SPECIAL_DATE_TIME = (5 << 2);
	public static final int SPECIAL_DOUBLE = (6 << 2);
	public static final int SPECIAL_CHAR = (7 << 2);
	public static final int SPECIAL_FLOAT = (8 << 2);
	public static final int SPECIAL_SHORT = (9 << 2);
	public static final int SPECIAL_BYTE = (10 << 2);
	public static final int SPECIAL_BYTEARRAY = (11 << 2);
	
	private final int maxSize;

	protected RpcSerializer rpcSerializer;
	
	protected boolean packZeros = true;
	
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
	
	public final int getMaxSize() {
		return this.maxSize;
	}

	@Override
	public CachedData encode(Object o) {
		byte[] b = null;
		int flags = SPECIAL_STRING;
		if (o instanceof String) {
			b = StringUtils.getBytesUtf8((String) o);
		} else if (o instanceof Long) {
			b = SerializerUtils.encodeLong((long) o, packZeros);
			flags |= SPECIAL_LONG;
		} else if (o instanceof Integer) {
			b = SerializerUtils.encodeInt((int) o, packZeros);
			flags |= SPECIAL_INT;
		} else if (o instanceof Boolean) {
			b = SerializerUtils.encodeBoolean((boolean) o);
			flags |= SPECIAL_BOOLEAN;
		} else if (o instanceof Date) {
			b = SerializerUtils.encodeLong(((Date) o).getTime(), packZeros);
			flags |= SPECIAL_DATE;
		} else if (o instanceof DateTime) {
			b = SerializerUtils.encodeLong(((DateTime) o).getMillis(), packZeros);
			flags |= SPECIAL_DATE;
		} else if (o instanceof Double) {
			b = SerializerUtils.encodeDouble((double) o, packZeros);
			flags |= SPECIAL_DOUBLE;
		} else if (o instanceof Character) {
			b = SerializerUtils.encodeChar((char) o, packZeros);
			flags |= SPECIAL_CHAR;
		} else if (o instanceof Float) {
			b = SerializerUtils.encodeFloat((float) o, packZeros);
			flags |= SPECIAL_FLOAT;
		} else if (o instanceof Short) {
			b = SerializerUtils.encodeShort((short) o, packZeros);
			flags |= SPECIAL_SHORT;
		} else if (o instanceof Byte) {
			b = SerializerUtils.encodeByte((byte) o);
			flags |= SPECIAL_BYTE;
		} else if (o instanceof byte[]) {
			b = (byte[]) o;
			flags |= SPECIAL_BYTEARRAY;
		} else {
			b = rpcSerializer.serialize(o);
			flags |= SERIALIZED;
		}
		assert b != null;
		if (b.length > this.compressionThreshold) {
			byte[] compressed = compress(b);
			if (compressed.length < b.length) {
				if (log.isDebugEnabled()) {
					log.debug("Compressed " + o.getClass().getName() + " from " + b.length + " to " + compressed.length);
				}
				b = compressed;
				flags |= COMPRESSED;
			} else {
				if (log.isDebugEnabled()) {
					log.debug("Compression increased the size of " + o.getClass().getName() + " from " + b.length + " to " + compressed.length);
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
	
	protected final Object decode0(CachedData cachedData, byte[] data, int flags) {
		Object rv = null;
		if ((flags & SERIALIZED) != 0 && data != null) {
			rv = rpcSerializer.deserialize(data, Object.class);
		} else {
			if (flags != SPECIAL_STRING && data != null) {
				switch (flags) {
				case SPECIAL_LONG:
					rv = SerializerUtils.decodeLong(data);
					break;
				case SPECIAL_INT:
					rv = SerializerUtils.decodeInt(data);
					break;
				case SPECIAL_BOOLEAN:
					rv = SerializerUtils.decodeBoolean(data);
					break;
				case SPECIAL_DATE:
					rv = new Date(SerializerUtils.decodeLong(data));
					break;
				case SPECIAL_DATE_TIME:
					rv = new DateTime(SerializerUtils.decodeLong(data));
					break;
				case SPECIAL_DOUBLE:
					rv = SerializerUtils.decodeDouble(data);
					break;			
				case SPECIAL_CHAR:
					rv = SerializerUtils.decodeChar(data);
					break;	
				case SPECIAL_FLOAT:
					rv = SerializerUtils.decodeFloat(data);
					break;
				case SPECIAL_SHORT:
					rv = SerializerUtils.decodeShort(data);
					break;
				case SPECIAL_BYTE:
					rv = SerializerUtils.decodeByte(data);
					break;
				case SPECIAL_BYTEARRAY:
					rv = data;
					break;
				default:
					log.warn(String.format("Undecodeable with flags %x", flags));
				}
			} else {
				rv = StringUtils.newStringUtf8(data);
			}
		}
		return rv;
	}
	
	
	/** 始终为false */
	@Override
	public final boolean isPrimitiveAsString() {
		return false;
	}
	
	/** 空实现。 */
	@Override
	public final void setPrimitiveAsString(boolean primitiveAsString) { }

	@Override
	public boolean isPackZeros() {
		return packZeros;
	}

	@Override
	public void setPackZeros(boolean packZeros) {
		this.packZeros = packZeros;
	}

	/** 空实现。 */
	@Override
	public void setCompressionMode(CompressionMode compressMode) { }

	public void setRpcSerializer(RpcSerializer rpcSerializer) {
		this.rpcSerializer = rpcSerializer;
	}
}
