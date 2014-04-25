package org.danielli.xultimate.core.support;

import org.danielli.xultimate.core.ClassTypeSupporter;
import org.danielli.xultimate.util.ArrayUtils;
import org.danielli.xultimate.util.reflect.ClassUtils;

public class DefaultClassTypeSupporter implements ClassTypeSupporter {
	
	/** 支持类型列表 */
	protected Class<?>[] supportClassTypes;
	
	@Override
	public boolean support(Class<?> classType) {
		if (ArrayUtils.isNotEmpty(supportClassTypes)) {
			for (Class<?> c : supportClassTypes) {
				if (ClassUtils.isAssignable(c, classType)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 设置支持类型列表。
	 * @param supportClassTypes 支持类型列表
	 */
	public void setSupportClassTypes(Class<?>[] supportClassTypes) {
		this.supportClassTypes = supportClassTypes;
	}
}
