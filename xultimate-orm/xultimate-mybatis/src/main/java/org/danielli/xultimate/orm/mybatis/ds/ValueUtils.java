package org.danielli.xultimate.orm.mybatis.ds;

import org.danielli.xultimate.orm.mybatis.ds.support.GenericValue;
import org.danielli.xultimate.orm.mybatis.ds.support.Pair;
import org.danielli.xultimate.orm.mybatis.ds.support.StringValue;

public class ValueUtils {
	public static <T extends Comparable<T>> Value<Pair<T>> newValue(T begin, T end) {
		GenericValue<Pair<T>> value = new GenericValue<>();
		Pair<T> pair = new Pair<>();
		pair.setFirst(begin);
		pair.setSecond(end);
		value.setValue(pair);
		return value;
	}
	
	public static <T extends Object> Value<T> newValue(T value) {
		GenericValue<T> genericValue = new GenericValue<>();
		genericValue.setValue(value);
		return genericValue;
	}
	
	public static Value<String> newValue(boolean hasLeftPercent, String pattern, boolean hasRightPercent) {
		StringValue stringValue = new StringValue();
		stringValue.setHasLeftPercent(hasLeftPercent);
		stringValue.setSourceValue(pattern);
		stringValue.setHasRightPercent(hasRightPercent);
		return stringValue;
	}
}
