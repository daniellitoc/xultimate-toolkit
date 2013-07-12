package org.danielli.xultimate.core.json.jackson;

import java.lang.reflect.Type;

import org.danielli.xultimate.core.json.ValueType;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * {@link TypeReference}适配器。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 * @see TypeReference
 * 
 * @param <T>
 */
public class ValueTypeAdapter<T> extends TypeReference<T> {
	
	private ValueType<T> valueType;
	
	public ValueTypeAdapter(ValueType<T> valueType) {
		this.valueType = valueType;
	}
	
	@Override
	public Type getType() {
		return valueType.getType();
	}
}
