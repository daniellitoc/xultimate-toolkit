package org.danielli.xultimate.shard;


/**
 * 过滤器。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 *
 * @param <S> 原类型。
 * @param <T> 处理后的类型。
 */
public interface Filter<S, T> {
	
	/**
	 * 过滤。
	 * @param source 原实例。
	 * @return 处理后的实例。
	 */
	T doFilter(S source);
}
