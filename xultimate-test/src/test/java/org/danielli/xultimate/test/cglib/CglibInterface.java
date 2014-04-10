package org.danielli.xultimate.test.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

import org.danielli.xultimate.test.cglib.callback.DefaultActionInvocationHandler;
import org.danielli.xultimate.util.ArrayUtils;

public class CglibInterface {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/tmp");
		DefaultActionInvocationHandler handler = new DefaultActionInvocationHandler();
		Enhancer enhancer = new Enhancer();
		enhancer.setCallbacks(ArrayUtils.toArray(handler));
		enhancer.setInterfaces(ArrayUtils.toArray(Action.class));
		enhancer.setUseCache(true);
		enhancer.create();
	}
}
