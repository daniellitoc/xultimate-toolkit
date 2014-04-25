package org.danielli.xultimate.core.serializer;

import org.danielli.xultimate.core.ClassTypeSupporter;

public abstract class AbstractClassTypeSupporterSerializer implements ClassTypeSupporterSerializer {

	protected ClassTypeSupporter classTypeSupporter;
	
	@Override
	public boolean support(Class<?> classType) {
		return classTypeSupporter.support(classType);
	}

	public void setClassTypeSupporter(ClassTypeSupporter classTypeSupporter) {
		this.classTypeSupporter = classTypeSupporter;
	}
}
