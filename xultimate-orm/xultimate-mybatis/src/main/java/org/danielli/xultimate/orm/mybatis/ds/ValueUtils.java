package org.danielli.xultimate.orm.mybatis.ds;

import org.danielli.xultimate.orm.mybatis.ds.support.GenericValue;
import org.danielli.xultimate.orm.mybatis.ds.support.Pair;
import org.danielli.xultimate.orm.mybatis.ds.support.StringValue;
import org.joda.time.DateTime;

/**
 * Value工具类。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
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
	
	public static void main(String[] args) {
		System.out.println(new DateTime(1960, 5, 1, 10, 10, 12).getMillis());
		//-305156989000
		//-305156988000
		
	}
}
