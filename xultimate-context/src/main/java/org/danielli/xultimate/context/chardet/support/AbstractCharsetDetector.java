package org.danielli.xultimate.context.chardet.support;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Set;

import org.danielli.xultimate.context.chardet.CharsetDetector;
import org.danielli.xultimate.context.chardet.CharsetDetectorException;
import org.danielli.xultimate.util.io.IOUtils;

/**
 * 抽象字符集检测器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see CharsetDetector
 * 
 * @param <S> 输入源。
 */
public abstract class AbstractCharsetDetector<S> implements CharsetDetector<S> {
	/** 字符集检测器。用于处理{@link InputStream}类型的。 */
	private CharsetDetector<InputStream> charsetDetector;	
	
	/**
	 * 根据输入流打开一个输入流。
	 * 
	 * @param source 输入源，如{@link URL}或${@link File}等。
	 * @return 指定输入流的输入流。
	 * @throws CharsetDetectorException 处理过程中出现的异常，都使用{@link CharsetDetectorException}包装并抛出。
	 */
	public abstract InputStream createInputStream(S source) throws CharsetDetectorException;
	
	public Set<Charset> detect(S source) throws CharsetDetectorException {
		InputStream inputStream = null;
		try {
			inputStream = createInputStream(source);
			return charsetDetector.detect(inputStream);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
	}

	/**
	 * 设置字符集检测器。用于处理{@link InputStream}类型的。
	 * 
	 * @param charsetDetector 字符集检测器。用于处理{@link InputStream}类型的。
	 */
	public void setCharsetDetector(CharsetDetector<InputStream> charsetDetector) {
		this.charsetDetector = charsetDetector;
	}
}
