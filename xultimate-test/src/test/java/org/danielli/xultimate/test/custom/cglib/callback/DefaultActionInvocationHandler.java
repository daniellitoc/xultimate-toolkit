package org.danielli.xultimate.test.custom.cglib.callback;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.InvocationHandler;

import org.danielli.xultimate.test.custom.cglib.support.DefaultAction;

public class DefaultActionInvocationHandler implements InvocationHandler {

	private DefaultAction defaultAction = new DefaultAction();
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("DefaultActionInvocationHandler.invoke.before()");
		Object result = method.invoke(defaultAction, args);
		System.out.println("DefaultActionInvocationHandler.invoke.after()");
		return result;
	}

}
