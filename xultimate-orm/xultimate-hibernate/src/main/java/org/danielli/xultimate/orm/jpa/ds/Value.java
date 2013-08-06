package org.danielli.xultimate.orm.jpa.ds;

import java.io.Serializable;

/**
 * 动态查询的值。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 *
 * @param <T> 值。
 */
public interface Value<T> extends Serializable {
	
	T getValue();
}
