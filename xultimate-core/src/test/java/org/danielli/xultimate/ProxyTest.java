package org.danielli.xultimate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.cglib.proxy.Enhancer;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;

public class ProxyTest {
	
	private Map<Integer, ProxyObject> proxyObjects = new ConcurrentHashMap<Integer, ProxyObject>();
	private ThreadLocal<Integer> keyThreadLocal = new ThreadLocal<>();

	class JDKInvocationHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			ProxyObject object = proxyObjects.get(keyThreadLocal.get());
			return method.invoke(object, args);
		}
	}
	
	class CglibInvocationHandler implements net.sf.cglib.proxy.InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			ProxyObject object = proxyObjects.get(keyThreadLocal.get());
			return method.invoke(object, args);
		}
	}
	
	class SpringAOP implements MethodInterceptor {

		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			ProxyObject object = proxyObjects.get(keyThreadLocal.get());
			return invocation.getMethod().invoke(object, invocation.getArguments());
		}
		
	}
	
	@Test
	public void test() {
		for (int i = 0; i < 100; i++) {
			proxyObjects.put(i, new DefaultProxyObject(i));
		}
		
		ProxyObject jdkObject = (ProxyObject) Proxy.newProxyInstance(ProxyObject.class.getClassLoader(), new Class<?>[] { ProxyObject.class }, new JDKInvocationHandler());
	
		Enhancer enhancer = new Enhancer();
		enhancer.setInterfaces(new Class<?>[] { ProxyObject.class });
		enhancer.setCallback(new CglibInvocationHandler());
		ProxyObject cglibObject = (ProxyObject) enhancer.create();
		
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTargetClass(ProxyObject.class);
		proxyFactory.addAdvice(new SpringAOP());
		proxyFactory.setOptimize(true);
		ProxyObject springObject = (ProxyObject) proxyFactory.getProxy(ProxyObject.class.getClassLoader());
		
		
		PerformanceMonitor.start("ProxyTest");
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 2000000; j++) {
				keyThreadLocal.set(j % 100);
				jdkObject.getProxy();
			}
			PerformanceMonitor.mark("jdkObject" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 2000000; j++) {
				keyThreadLocal.set(j % 100);
				cglibObject.getProxy();
			}
			PerformanceMonitor.mark("cglibObject" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 2000000; j++) {
				keyThreadLocal.set(j % 100);
				springObject.getProxy();
			}
			PerformanceMonitor.mark("springObject" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
		
	}
}




interface ProxyObject {
	String getProxy();
}

class DefaultProxyObject implements ProxyObject {
	
	private Integer value;
	
	public DefaultProxyObject(Integer value) {
		this.value = value;
	}
	
	@Override
	public String getProxy() {
		return "Proxy" + value;
	}
}
