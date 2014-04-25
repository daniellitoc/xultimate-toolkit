package org.danielli.xultimate.core.compression;

import java.io.InputStream;

/**
 * 解压缩器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 *
 * @param <S> 原始源。
 * @param <T> 目标源。
 */
public interface Decompressor<S, T> {
	
	/**
	 * 解压缩。
	 * 
	 * @param source 原始源。
	 * @return 目标源。
	 * @throws DecompressorException 解压缩异常，在解压缩过程中出现的任何异常都会使用{@link DecompressorException}封装并抛出。
	 */
	T decompress(S source) throws DecompressorException;
	
	/**
	 * 包装输入流。
	 * 
	 * @param sourceInputStream 原始输入流。
	 * @return 包装后的输入流。
	 * @throws DecompressorException 解压缩异常，在包装过程中出现的任何异常都会使用{@link DecompressorException}封装并抛出。
	 */
	InputStream wrapper(InputStream sourceInputStream) throws DecompressorException;
}
