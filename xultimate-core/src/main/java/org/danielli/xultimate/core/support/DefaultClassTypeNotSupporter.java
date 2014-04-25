package org.danielli.xultimate.core.support;

import org.danielli.xultimate.core.ClassTypeSupporter;
import org.danielli.xultimate.util.ArrayUtils;
import org.danielli.xultimate.util.reflect.ClassUtils;

public class DefaultClassTypeNotSupporter implements ClassTypeSupporter{

	/** 不支持类型列表 */
	protected Class<?>[] notSupportClassTypes;

	@Override
	public boolean support(Class<?> classType) {
		if (ArrayUtils.isNotEmpty(notSupportClassTypes)) {
			for (Class<?> c : notSupportClassTypes) {
				if (ClassUtils.isAssignable(c, classType)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 设置不支持类型列表。
	 * @param notSupportClassTypes 不支持类型列表
	 */
	public void setNotSupportClassTypes(Class<?>[] notSupportClassTypes) {
		this.notSupportClassTypes = notSupportClassTypes;
	}

}
