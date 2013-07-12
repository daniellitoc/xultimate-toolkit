package org.danielli.xultimate.core;

/**
 * 类型支持器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public interface ClassTypeSupporter {
	
	
	/**
	 * 是否支持此类型。
	 * @param classType 类型。
	 * @return 如果为true，则表示可以，否则为false。
	 */
	boolean support(Class<?> classType);

}
