package org.danielli.xultimate.context.crypto.support;

import org.danielli.xultimate.context.codec.Decoder;
import org.danielli.xultimate.context.codec.Encoder;
import org.danielli.xultimate.context.crypto.DecryptorException;
import org.danielli.xultimate.context.crypto.EncryptorException;
import org.danielli.xultimate.util.StringUtils;

/**
 * 处理{@link String}到{@link String}类型的编码和解码器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see AbstractCryptor
 */
public class StringStringCryptor extends AbstractCryptor<String, String, byte[], byte[]> {
	
	/** 编码器 */
	private Encoder<byte[], String> encoder;
	/** 解码器 */
	private Decoder<String, byte[]> decoder;

	/**
	 * 设置编码器。
	 * @param encoder 编码器。
	 */
	public void setEncoder(Encoder<byte[], String> encoder) {
		this.encoder = encoder;
	}

	/**
	 * 设置解码器。
	 * @param decoder 解码器。
	 */
	public void setDecoder(Decoder<String, byte[]> decoder) {
		this.decoder = decoder;
	}

	@Override
	public String encrypt(String source) throws EncryptorException {
		return encoder.encode(encryptor.encrypt(StringUtils.getBytesUtf8(source)));
	}

	@Override
	public String decrypt(String source) throws DecryptorException {
		return StringUtils.newStringUtf8(decryptor.decrypt(decoder.decode(source)));
	}

}
