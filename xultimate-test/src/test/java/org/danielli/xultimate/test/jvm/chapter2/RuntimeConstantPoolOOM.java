package org.danielli.xultimate.test.jvm.chapter2;

import java.util.ArrayList;
import java.util.List;

/**
 * VM Args: -XX:PermSize=5m -XX:MaxPermSize=5m
 * 
 * JDK 1.7中不会出现这个问题，String.intern()返回的是首次出现的实例引用。见
 * 
 * @author Daniel Li
 */
public class RuntimeConstantPoolOOM {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		int i = 0;
		while (true) {
			list.add(String.valueOf(i++).intern());
		}
	}
}
