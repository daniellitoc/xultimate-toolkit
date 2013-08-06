package org.danielli.xultimate.test.jvm.chapter3;

/**
 * VM Args: -XX:+UseSerialGC -XX:+PrintGCDetails
 * 
 * @author Daniel Li
 */
public class ReferenceCountingGC {
	
	public Object instance = null;
	
	private static final int _1MB = 1024 * 1024;
	
	@SuppressWarnings("unused")
	private byte[] bigSize = new byte[2 * _1MB];
	
	public static void testGC() {
		ReferenceCountingGC objA = new ReferenceCountingGC();
		ReferenceCountingGC objB = new ReferenceCountingGC();
		objA.instance = objB;
		objB.instance = objA;
		
		objA = null;
		objB = null;
		System.gc();
	}
	
	public static void main(String[] args) {
		testGC();
	}
}
