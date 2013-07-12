package org.danielli.xultimate.context.codec;

/**
 * 编码器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 *
 * @param <S> 原始源。
 * @param <T> 目标源。
 */
public interface Encoder<S, T> {
	
	/**
	 * 编码。
	 * 
	 * @param source 原始源。
	 * @return 目标源。
	 * @throws EncoderException 编码异常，在编码过程中出现的任何异常都会使用{@link EncoderException}封装并抛出。
	 */
	T encode(S source) throws EncoderException;
}
