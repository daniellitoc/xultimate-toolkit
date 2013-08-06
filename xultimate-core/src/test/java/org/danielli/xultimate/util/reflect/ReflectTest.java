package org.danielli.xultimate.util.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;

import com.esotericsoftware.reflectasm.MethodAccess;

public class ReflectTest {
	
	@Test
	public void test() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		User someObject = new User();
		PerformanceMonitor.start("ReflectTest");
		// 禁止使用这种方式
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 1000000; j++) {
				User.class.getMethod("setUsername", String.class).invoke(someObject, "Daniel Li");
			}
			PerformanceMonitor.mark("JDK Simple" + i);
		}
		
		Method method = User.class.getMethod("setUsername", String.class);
		// 超过sun.reflect.inflationThreshold个阀值后会被编译成直接代码。
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 1000000; j++) {
				method.invoke(someObject, "Daniel Li");
			}
			PerformanceMonitor.mark("JDK" + i);
		}
		
		MethodAccess access = MethodAccess.get(User.class);
		// 禁止使用这种方式，没有Java反射速度快。
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 1000000; j++) {
				access.invoke(someObject, "setUsername", "Daniel Li");
			}
			PerformanceMonitor.mark("Reflect Simple" + i);
		}

		// 特殊情况使用。
		int fooIndex = access.getIndex("setUsername", String.class);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 1000000; j++) {
				access.invoke(someObject, fooIndex, "Daniel Li");
			}
			PerformanceMonitor.mark("Reflect" + i);
		}
		
		// 没有JDK速度快。
		FastClass clazz = FastClass.create(User.class); 
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 1000000; j++) {
				clazz.invoke("setUsername", new Class[] { String.class }, someObject, new Object[] { "Daniel Li" });
			}
			PerformanceMonitor.mark("FastClass Simple" + i);
		}
		
		FastMethod fastMethod = clazz.getMethod("setUsername", new Class[] { String.class });
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 1000000; j++) {
				fastMethod.invoke(someObject, new Object[] { "Daniel Li" });
			}
			PerformanceMonitor.mark("FastClass" + i);
		}
		
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
}

class User {
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
