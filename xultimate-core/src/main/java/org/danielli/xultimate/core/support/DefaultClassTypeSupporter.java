package org.danielli.xultimate.core.support;

import org.danielli.xultimate.core.ClassTypeSupporter;

public class DefaultClassTypeSupporter implements ClassTypeSupporter {
	
	@Override
	public boolean support(Class<?> classType) {
		return true;
	}
}
