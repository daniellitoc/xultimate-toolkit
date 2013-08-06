package org.danielli.xultimate.orm.mybatis.ds;

import java.io.Serializable;

public interface Value<T> extends Serializable {
	T getValue();
}
