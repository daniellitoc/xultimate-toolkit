package org.danielli.xultimate.test.cglib;

import org.danielli.xultimate.test.cglib.callback.DefaultActionMethodInterceptor;
import org.danielli.xultimate.test.cglib.support.DefaultAction;
import org.danielli.xultimate.util.ArrayUtils;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

public class CglibClass {
	
	public static void main(String[] args) {
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/tmp");
		DefaultActionMethodInterceptor interceptor = new DefaultActionMethodInterceptor();
		Enhancer enhancer = new Enhancer();
		enhancer.setCallbacks(ArrayUtils.toArray(interceptor));
		enhancer.setSuperclass(DefaultAction.class);
		enhancer.setUseCache(true);
		enhancer.create();
	}
}
