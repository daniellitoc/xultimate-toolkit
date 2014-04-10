package org.danielli.xultimate.test.cglib.callback;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class DefaultActionMethodInterceptor implements MethodInterceptor {

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("DefaultActionMethodInterceptor.intercept.before()");
		Object result = proxy.invokeSuper(obj, args);
		System.out.println("DefaultActionMethodInterceptor.intercept.after()");
		return result;
	}

}
