package org.danielli.xultimate.context.kvStore.memcached.xmemcached;

import java.util.Date;

import org.danielli.xultimate.core.serializer.RpcSerializer;
import org.danielli.xultimate.core.serializer.java.BooleanSerializer;
import org.danielli.xultimate.core.serializer.java.ByteSerializer;
import org.danielli.xultimate.core.serializer.java.DateSerializer;
import org.danielli.xultimate.core.serializer.java.DoubleSerializer;
import org.danielli.xultimate.core.serializer.java.FloatSerializer;
import org.danielli.xultimate.core.serializer.java.IntegerSerializer;
import org.danielli.xultimate.core.serializer.java.LongSerializer;
import org.danielli.xultimate.core.serializer.java.StringSerializer;

import net.rubyeye.xmemcached.transcoders.CachedData;
import net.rubyeye.xmemcached.transcoders.Transcoder;

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
	public static final int SPECIAL_MASK = 0xff00;
	public static final int SPECIAL_BOOLEAN = (1 << 8);
	public static final int SPECIAL_INT = (2 << 8);
	public static final int SPECIAL_LONG = (3 << 8);
	public static final int SPECIAL_DATE = (4 << 8);
	public static final int SPECIAL_BYTE = (5 << 8);
	public static final int SPECIAL_FLOAT = (6 << 8);
	public static final int SPECIAL_DOUBLE = (7 << 8);
	public static final int SPECIAL_BYTEARRAY = (8 << 8);
	
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
	
	private StringSerializer stringSerializer;
	
	private LongSerializer longSerializer;
	
	private IntegerSerializer integerSerializer;
	
	private BooleanSerializer booleanSerializer;
	
	private ByteSerializer byteSerializer;
	
	private DateSerializer dateSerializer;
	
	private FloatSerializer floatSerializer;
	
	private DoubleSerializer doubleSerializer;
	
	private RpcSerializer rpcSerializer;
	
	@Override
	public CachedData encode(Object o) {
		byte[] b = null;
		int flags = 0;
		Class<? extends Object> clazz = o.getClass();
		if (stringSerializer.support(clazz)) {
			b = stringSerializer.serialize(o);
		} else if (longSerializer.support(clazz)) {
			b = longSerializer.serialize(o);
			flags |= SPECIAL_LONG;
		} else if (integerSerializer.support(clazz)) {
			b = integerSerializer.serialize(o);
			flags |= SPECIAL_INT;
		} else if (booleanSerializer.support(clazz)) {
			b = booleanSerializer.serialize(o);
			flags |= SPECIAL_BOOLEAN;
		} else if (dateSerializer.support(clazz)) {
			b = dateSerializer.serialize(o);
			flags |= SPECIAL_DATE;
		} else if (byteSerializer.support(clazz)) {
			b = byteSerializer.serialize(o);
			flags |= SPECIAL_BYTE;
		} else if (floatSerializer.support(clazz)) {
			b = floatSerializer.serialize(o);
			flags |= SPECIAL_FLOAT;
		} else if (doubleSerializer.support(clazz)) {
			b = doubleSerializer.serialize(o);
			flags |= SPECIAL_DOUBLE;
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
		flags = flags & SPECIAL_MASK;
		return decode0(d,data, flags);
	}
	
	protected final Object decode0(CachedData cachedData,byte[] data, int flags) {
		Object rv = null;
		if ((cachedData.getFlag() & SERIALIZED) != 0 && data != null) {
			rv = rpcSerializer.deserialize(data, Object.class);
		} else {
			if (flags != 0 && data != null) {
				switch (flags) {
				case SPECIAL_BOOLEAN:
					rv = booleanSerializer.deserialize(data, Boolean.class);
					break;
				case SPECIAL_INT:
					rv = integerSerializer.deserialize(data, Integer.class);
					break;
				case SPECIAL_LONG:
					rv = longSerializer.deserialize(data, Long.class);
					break;
				case SPECIAL_BYTE:
					rv = byteSerializer.deserialize(data, Byte.class);
					break;
				case SPECIAL_FLOAT:
					rv = floatSerializer.deserialize(data, Float.class);
					break;
				case SPECIAL_DOUBLE:
					rv = doubleSerializer.deserialize(data, Double.class);
					break;
				case SPECIAL_DATE:
					rv = dateSerializer.deserialize(data, Date.class);
					break;
				case SPECIAL_BYTEARRAY:
					rv = data;
					break;
				default:
					log.warn(String.format("Undecodeable with flags %x", flags));
				}
			} else {
				rv = stringSerializer.deserialize(data, String.class);
			}
		}
		return rv;
	}

	@Override
	public void setPrimitiveAsString(boolean primitiveAsString) { }

	@Override
	public void setPackZeros(boolean packZeros) { }

	public StringSerializer getStringSerializer() {
		return stringSerializer;
	}

	public void setStringSerializer(StringSerializer stringSerializer) {
		this.stringSerializer = stringSerializer;
	}

	public LongSerializer getLongSerializer() {
		return longSerializer;
	}

	public void setLongSerializer(LongSerializer longSerializer) {
		this.longSerializer = longSerializer;
	}

	public IntegerSerializer getIntegerSerializer() {
		return integerSerializer;
	}

	public void setIntegerSerializer(IntegerSerializer integerSerializer) {
		this.integerSerializer = integerSerializer;
	}

	public BooleanSerializer getBooleanSerializer() {
		return booleanSerializer;
	}

	public void setBooleanSerializer(BooleanSerializer booleanSerializer) {
		this.booleanSerializer = booleanSerializer;
	}

	public ByteSerializer getByteSerializer() {
		return byteSerializer;
	}

	public void setByteSerializer(ByteSerializer byteSerializer) {
		this.byteSerializer = byteSerializer;
	}

	public DateSerializer getDateSerializer() {
		return dateSerializer;
	}

	public void setDateSerializer(DateSerializer dateSerializer) {
		this.dateSerializer = dateSerializer;
	}

	public FloatSerializer getFloatSerializer() {
		return floatSerializer;
	}

	public void setFloatSerializer(FloatSerializer floatSerializer) {
		this.floatSerializer = floatSerializer;
	}

	public DoubleSerializer getDoubleSerializer() {
		return doubleSerializer;
	}

	public void setDoubleSerializer(DoubleSerializer doubleSerializer) {
		this.doubleSerializer = doubleSerializer;
	}

	public RpcSerializer getRpcSerializer() {
		return rpcSerializer;
	}

	public void setRpcSerializer(RpcSerializer rpcSerializer) {
		this.rpcSerializer = rpcSerializer;
	}
}
