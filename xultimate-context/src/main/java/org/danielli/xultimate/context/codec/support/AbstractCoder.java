package org.danielli.xultimate.context.codec.support;

import org.danielli.xultimate.context.codec.Decoder;
import org.danielli.xultimate.context.codec.DecoderException;
import org.danielli.xultimate.context.codec.Encoder;
import org.danielli.xultimate.context.codec.EncoderException;

/**
 * 抽象编码和解码器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see Encoder
 * @see Decoder
 *
 * @param <S> 原始源。
 * @param <T> 目标源。
 * @param <IS> 内部处理原始源。
 * @param <IT> 内部处理目标源。
 */
public abstract class AbstractCoder<S, T, IS, IT> implements Encoder<S, T>, Decoder<T, S> {
	/** 解码器，处理IT到IS类型。 */
	private Decoder<IT, IS> decoder;
	/** 编码器，处理IS到IT类型。 */
	private Encoder<IS, IT> encoder;
	
	/**
	 * 内部编码。
	 * 
	 * @param source 原始源。
	 * @return 目标源。
	 * @throws EncoderException 编码异常，在编码过程中出现的任何异常都会使用{@link EncoderException}封装并抛出。
	 */
	public IT innerEncode(IS source) throws DecoderException {
		return encoder.encode(source);
	}
	
	/**
	 * 内部解码。
	 * 
	 * @param source 原始源。
	 * @return 目标源。
	 * @throws DecoderException 解码异常，在解码过程中出现的任何异常都会使用{@link DecoderException}封装并抛出。
	 */
	public IS innerDecode(IT source) throws DecoderException {
		return decoder.decode(source);
	}
	
	/**
	 * 设置解码器。
	 * @param decoder 解码器。处理IT到IS类型。
	 */
	public void setDecoder(Decoder<IT, IS> decoder) {
		this.decoder = decoder;
	}
	
	/**
	 * 设置编码器。
	 * @param encoder 编码器。处理IS到IT类型。
	 */
	public void setEncoder(Encoder<IS, IT> encoder) {
		this.encoder = encoder;
	}
	
}
