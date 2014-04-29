package org.danielli.xultimate.test.custom.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

import org.danielli.xultimate.test.custom.cglib.callback.DefaultActionMethodInterceptor;
import org.danielli.xultimate.test.custom.cglib.support.DefaultAction;
import org.danielli.xultimate.util.ArrayUtils;

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
