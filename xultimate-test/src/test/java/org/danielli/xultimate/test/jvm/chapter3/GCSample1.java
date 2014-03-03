package org.danielli.xultimate.test.jvm.chapter3;


/**
 * VM Args: -XX:+UseSerialGC -Xms20m -Xmx20m -Xmn10m -XX:SurvivorRatio=8 -XX:+PrintGCDetails
 * 
 * @author Daniel Li
 */
public class GCSample1 {
	
	private static final int _1MB = 1024 * 1024;
	
	@SuppressWarnings("unused")
	public static void testAllocation() {
		byte[] allocation1, allocation2, allocation3, allocation4;
		allocation1 = new byte[2 * _1MB];
		allocation2 = new byte[2 * _1MB];
		allocation3 = new byte[2 * _1MB];
		allocation4 = new byte[4 * _1MB];
	}
	
	public static void main(String[] args) {
		testAllocation();
	}
}
