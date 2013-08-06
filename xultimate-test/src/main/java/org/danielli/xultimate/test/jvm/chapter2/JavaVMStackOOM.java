package org.danielli.xultimate.test.jvm.chapter2;

/**
 * VM Args: -Xss2m
 * 
 * @author Daniel Li
 */
public class JavaVMStackOOM {
	private void dontStop() {
		while (true) {
			
		}
	}
	
	public void stackLeakByThread() {
		while (true) {
			Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					dontStop();
				}
			});
			thread.start();
		}
	}
	
	public static void main(String[] args) {
		JavaVMStackOOM oom = new JavaVMStackOOM();
		oom.stackLeakByThread();
	}
}
