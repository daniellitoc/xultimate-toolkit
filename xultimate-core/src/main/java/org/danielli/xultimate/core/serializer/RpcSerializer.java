package org.danielli.xultimate.core.serializer;


public abstract class RpcSerializer implements ClassTypeSupporterSerializer {
	
	@Override
	public boolean support(Class<?> classType) {
		return true;
	}
	
}
