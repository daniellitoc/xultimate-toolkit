package org.danielli.xultimate.context.chardet;

import java.nio.charset.Charset;
import java.util.Set;

/**
 * 字符集检测器。
 *
 * @author Daniel Li
 * @since 18 Jun 2013
 * 
 * @param <S> 输入源。
 */
public interface CharsetDetector<S> {
	
	/**
	 * 检测指定输入源的字符集。
	 * 
	 * @param source 输入源。
	 * @return 字符集集合。
	 * @throws CharsetDetectorException 检测过程中的出现的异常，都由{@link CharsetDetectorException }封装并抛出。
	 */
	Set<Charset> detect(S source) throws CharsetDetectorException;
}
