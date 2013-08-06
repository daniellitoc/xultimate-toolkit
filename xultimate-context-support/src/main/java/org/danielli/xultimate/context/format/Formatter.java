package org.danielli.xultimate.context.format;

/**
 * 格式化器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 *
 * @param <S> 原始源。
 * @param <P> 源始源参数。
 * @param <T> 目标源。
 */
public interface Formatter<S, P, T> {
	
	/**
	 * 格式化。
	 * 
	 * @param source 原始源。
	 * @param parameter 源始源参数。
	 * @return 目标源。
	 * @throws FormatException 格式化异常，会对格式化过程中出现的异常封装并抛出。
	 */
	T format(S source, P parameter) throws FormatException;
	
}
