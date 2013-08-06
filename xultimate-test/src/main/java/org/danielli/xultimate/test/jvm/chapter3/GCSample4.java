package org.danielli.xultimate.test.jvm.chapter3;

/**
 * VM Args: -XX:+UseSerialGC -Xms20m -Xmx20m -Xmn10m -XX:SurvivorRatio=8 -XX:+PrintGCDetails -XX:MaxTenuringThreshold=15
 * 
 * @author Daniel Li
 */
public class GCSample4 {
	
	private static final int _1MB = 1024 * 1024;
	
	@SuppressWarnings("unused")
	public static void testTenuringThreshold2() {
		byte[] allocation1, allocation2, allocation3, allocation4;
		allocation1 = new byte[_1MB / 4];
		allocation2 = new byte[_1MB / 4];
		allocation3 = new byte[4 * _1MB];
		allocation4 = new byte[4 * _1MB];
		allocation4 = null;
		allocation4 = new byte[4 * _1MB];
	}
	
	public static void main(String[] args) {
		testTenuringThreshold2();
	}
}
