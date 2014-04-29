package org.danielli.xultimate.test.custom;

public class ErrorTest {
	
	public static void main(String[] args) {
		try {
			testError();
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	public static void testError() {
		throw new OutOfMemoryError();
	}
}
