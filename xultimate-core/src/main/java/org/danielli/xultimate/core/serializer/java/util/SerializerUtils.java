package org.danielli.xultimate.core.serializer.java.util;



public class SerializerUtils {
	
	public static final int LONG_BYTE_SIZE = 8;
	
	public static final int INT_BYTE_SIZE = 4;
	
	public static final int BYTE_BYTE_SIZE = 1;
	
	public static final int BOOLEAN_BYTE_SIZE = 1;
	
	public static final int SHORT_BYTE_SIZE = 2;
	
	private static byte[] encodeNum(long l, int maxBytes, boolean packZeros) {
		byte[] rv = new byte[maxBytes];
		for (int i = 0; i < rv.length; i++) {
			int pos = rv.length - i - 1;
			rv[pos] = (byte) ((l >> (8 * i)) & 0xff);
		}
		if (packZeros) {
			int firstNon0 = 0;
			for (; firstNon0 < rv.length && rv[firstNon0] == 0; firstNon0++) {
				// Just looking for what we can reduce
			}
			if (firstNon0 > 0) {
				byte[] tmp = new byte[rv.length - firstNon0];
				System.arraycopy(rv, firstNon0, tmp, 0, rv.length - firstNon0);
				rv = tmp;
			}
		}
		return rv;
	}
	
	public static byte[] encodeDouble(double value) {
		return encodeLong(Double.doubleToLongBits(value), false);
	}

	public static double decodeDouble(byte[] values) {
		return Double.longBitsToDouble(decodeLong(values));
	}

	public static byte[] encodeLong(long l, boolean packZeros) {
		return encodeNum(l, LONG_BYTE_SIZE, packZeros);
	}

	public static long decodeLong(byte[] b) {
		assert b.length <= LONG_BYTE_SIZE : "Too long to be an long (" + b.length + ") bytes";
		long rv = 0;
		for (byte i : b) {
			rv = (rv << 8) | (i < 0 ? 256 + i : i);
		}
		return rv;
	}

	public static byte[] encodeFloat(float in) {
		return encodeInt(Float.floatToIntBits(in), false);
	}

	public static float decodeFloat(byte[] in) {
		return Float.floatToIntBits(decodeInt(in));
	}
	
	public static byte[] encodeInt(int in, boolean packZeros) {
		return encodeNum(in, INT_BYTE_SIZE, packZeros);
	}

	public static int decodeInt(byte[] in) {
		assert in.length <= INT_BYTE_SIZE : "Too long to be an int (" + in.length + ") bytes";
		return (int) decodeLong(in);
	}
	
	public static byte[] encodeShort(short in, boolean packZeros) {
		return encodeNum(in, SHORT_BYTE_SIZE, packZeros);
	}

	public static int decodeShort(byte[] in) {
		assert in.length <= SHORT_BYTE_SIZE : "Too long to be an short (" + in.length + ") bytes";
		return (int) decodeLong(in);
	}

	public static byte[] encodeChar(char in, boolean packZeros) {
		return encodeNum(in, SHORT_BYTE_SIZE, packZeros);
	}
	
	public static char decodeChar(byte[] in) {
		assert in.length <= SHORT_BYTE_SIZE : "Too long to be an char (" + in.length + ") bytes";
		return (char) decodeLong(in);
	}
	
	public static byte[] encodeByte(byte in) {
		return new byte[] { in };
	}

	public static byte decodeByte(byte[] in) {
		assert in.length == BYTE_BYTE_SIZE : "Too long for a byte";
		return in[0];
	}

	public static byte[] encodeBoolean(boolean b) {
		byte[] rv = new byte[BOOLEAN_BYTE_SIZE];
		rv[0] = (byte) (b ? 1 : 0);
		return rv;
	}

	public static boolean decodeBoolean(byte[] in) {
		assert in.length == BOOLEAN_BYTE_SIZE : "Wrong length for a boolean";
		return in[0] == 1;
	}
}
