package org.danielli.xultimate.core.support;

import org.danielli.xultimate.core.ClassTypeSupporter;

public class AdvancedClassTypeSupporter implements ClassTypeSupporter {

	/** 不支持类型列表 */
	private Class<?>[] notSupportClassTypes;
	
	@Override
	public boolean support(Class<?> classType) {
		if (classType.isArray()) {
			return false;
		}
		if (notSupportClassTypes != null) {
			for (Class<?> c : notSupportClassTypes) {
				if (c.isAssignableFrom(classType)) {
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
