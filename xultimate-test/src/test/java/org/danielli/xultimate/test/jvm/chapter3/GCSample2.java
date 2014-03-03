package org.danielli.xultimate.test.jvm.chapter3;

/**
 * VM Args: -XX:+UseSerialGC -Xms20m -Xmx20m -Xmn10m -XX:SurvivorRatio=8 -XX:+PrintGCDetails -XX:PretenureSizeThreshold=3145728
 * 
 * @author Daniel Li
 */
public class GCSample2 {
	
	private static final int _1MB = 1024 * 1024;
	
	@SuppressWarnings("unused")
	public static void testPretenureSizeThreshold() {
		byte[] allocation;
		allocation = new byte[4 * _1MB];
	}
	
	public static void main(String[] args) {
		testPretenureSizeThreshold();
	}
}
