package org.danielli.xultimate.context.codec.support;

import org.apache.commons.codec.binary.Hex;
import org.danielli.xultimate.context.codec.Decoder;
import org.danielli.xultimate.context.codec.DecoderException;
import org.danielli.xultimate.context.codec.Encoder;
import org.danielli.xultimate.context.codec.EncoderException;

/**
 * Hex算法编码器。处理byte[]到char[]类型。
 * 
 * @author Daniel Li
 * @since 16 Jun 2013
 * @see Encoder
 * @see Decoder
 */
public class HexCodec implements Encoder<byte[], char[]>, Decoder<char[], byte[]>{

	@Override
	public byte[] decode(char[] source) throws DecoderException {
		try {
    		return Hex.decodeHex(source);
    	} catch (org.apache.commons.codec.DecoderException e) {
    		throw new DecoderException(e.getMessage(), e);
    	}
	}

	@Override
	public char[] encode(byte[] source) throws EncoderException {
		try {
			return Hex.encodeHex(source);
		} catch (Exception e) {
			throw new EncoderException(e.getMessage(), e);
		}
	}

}
