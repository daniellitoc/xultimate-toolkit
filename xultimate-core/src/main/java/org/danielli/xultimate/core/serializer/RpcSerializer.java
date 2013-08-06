package org.danielli.xultimate.core.serializer;

public abstract class RpcSerializer extends AbstractClassTypeSupportSerializer {
	
	@Override
	public boolean support(Class<?> classType) {
		return true;
	}
	
}
