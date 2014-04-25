package org.danielli.xultimate.core.compression;

import java.io.OutputStream;

/**
 * 压缩器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 *
 * @param <S> 原始源。
 * @param <T> 目标源。
 */
public interface Compressor<S, T> {
	
	/**
	 * 压缩。
	 * 
	 * @param source 原始源。
	 * @return 目标源。
	 * @throws CompressorException 压缩异常，在压缩过程中出现的任何异常都会使用{@link CompressorException}封装并抛出。
	 */
	T compress(S source) throws CompressorException;
	
	/**
	 * 包装输出流。
	 * 
	 * @param sourceInputStream 原始输出流。
	 * @return 包装后的输出流。
	 * @throws CompressorException 压缩异常，在包装过程中出现的任何异常都会使用{@link CompressorException}封装并抛出。
	 */
	OutputStream wrapper(OutputStream sourceOutputStream) throws CompressorException;
}
