package org.danielli.xultimate.test.jvm.chapter2;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * VM Args: -Xmx20m -XX:MaxDirectMemorySize=10m
 * 
 * @author Daniel Li
 */
public class DirectMemoryOOM {
	private static final int _1MB = 1024 * 1024;
	
	public static void main(String[] args) throws Throwable {
		Field unsafeField = DirectMemoryOOM.class.getClassLoader().loadClass("sun.misc.Unsafe").getDeclaredFields()[0];
		unsafeField.setAccessible(true);
		Object unsafe = unsafeField.get(null);
		Method unsafeMethod = unsafe.getClass().getDeclaredMethod("allocateMemory", long.class);
		int i = 0;
		while (true) {
			System.out.println(++i);
			unsafeMethod.invoke(unsafe, _1MB);
		}
	}
}
