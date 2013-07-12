package org.danielli.xultimate.beans.support;

import org.danielli.xultimate.beans.Holder;

/**
 * 对象包装器。
 * 
 * @author Daniel Li
 * @since 16 Jun 2013
 *
 * @param <T> 包装对象。
 */
public class ObjectHolder<T> implements Holder {

	private T value;
	
	public ObjectHolder() {
		value = null;
	}
	
	public ObjectHolder(T value) {
		this.value = value;
	}
	
	/**
	 * 获取包装对象。
	 * @return 包装对象。
	 */
	public T getObject() {
		return value;
	}

	/**
	 * 设置包装对象。
	 * @param value 包装对象。
	 */
	public void setValue(T value) {
		this.value = value;
	}
}
