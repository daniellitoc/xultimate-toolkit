package org.danielli.xultimate.test.jvm.chapter2;

/**
 * VM Args: -Xss128k
 * 
 * @author Daniel Li
 */
public class JavaVMStackSOF {
	private int stackLength = 1;
	
	public void stackLeak() {
//		long tmp1 = 1;
//		long tmp2 = 1;
//		long tmp3 = 1;
//		long tmp4 = 1;
//		long tmp5 = 1;
//		long tmp6 = 1;
//		long tmp7 = 1;
//		long tmp8 = 1;
//		long tmp9 = 1;
		stackLength++;
		stackLeak();
	}
	
	public static void main(String[] args) {
		JavaVMStackSOF oom = new JavaVMStackSOF();
		try {
			oom.stackLeak();
		} catch (Throwable e) {
			System.out.println("stack length: " + oom.stackLength);
			throw e;
		}
	}
}
