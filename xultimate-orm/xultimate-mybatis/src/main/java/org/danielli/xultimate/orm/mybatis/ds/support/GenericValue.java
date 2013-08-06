package org.danielli.xultimate.orm.mybatis.ds.support;

import org.danielli.xultimate.orm.mybatis.ds.Value;

public class GenericValue<T> implements Value<T> {
	private static final long serialVersionUID = 8776418271286892897L;
	
	private T value;
	
	@Override
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	
}
