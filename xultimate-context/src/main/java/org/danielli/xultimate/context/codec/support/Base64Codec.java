package org.danielli.xultimate.context.codec.support;

import org.apache.commons.codec.binary.Base64;
import org.danielli.xultimate.context.codec.Decoder;
import org.danielli.xultimate.context.codec.DecoderException;
import org.danielli.xultimate.context.codec.Encoder;
import org.danielli.xultimate.context.codec.EncoderException;

/**
 * Base64编码和解码器。处理byte[]到byte[]类型。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see Encoder
 * @see Decoder
 */
public class Base64Codec implements Encoder<byte[], byte[]>, Decoder<byte[], byte[]> {

	@Override
	public byte[] decode(byte[] source) throws DecoderException {
		try {
			return Base64.decodeBase64(source);
		} catch (Exception e) {
			throw new DecoderException(e.getMessage(), e);
		}
		
	}

	@Override
	public byte[] encode(byte[] source) throws EncoderException {
		try {
			return Base64.encodeBase64(source);
		} catch (Exception e) {
			throw new EncoderException(e.getMessage(), e);
		}
		
	}

}
