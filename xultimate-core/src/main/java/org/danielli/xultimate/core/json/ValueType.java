package org.danielli.xultimate.core.json;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.danielli.xultimate.core.json.JSONException;
import org.danielli.xultimate.core.json.ValueType;
import org.danielli.xultimate.util.Assert;

/**
 * 当JSON转换Java类型时，通过此类的范型元素标识转换的类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 *
 * @param <T> 被转换成的类型。
 */
public abstract class ValueType<T> {
	/** 范型元素的类型 */
	private Type type;
	
	public ValueType() {
		Type type = this.getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
			this.type = parameterizedType[0];
		}
		Assert.notNull(type, new JSONException("Type has not been set before usage"));
	}

	/**
	 * 获取范型元素的类型。
	 * 
	 * @return 范型元素的类型。
	 */
	public final Type getType() {
		return this.type;
	}
}


