package org.danielli.xultimate.test.jvm.chapter3;

/**
 * VM Args: -XX:+UseSerialGC
 * 
 * @author Daniel Li
 */
public class FinalizeEscapeGC {
	public static FinalizeEscapeGC SAVE_HOOK = null;
	
	public void isAlive() {
		System.out.println("yes, I am still alive");
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("finalize method executed!");
		FinalizeEscapeGC.SAVE_HOOK = this;
	}
	
	public static void main(String[] args) throws InterruptedException {
		SAVE_HOOK = new FinalizeEscapeGC();
		
		SAVE_HOOK = null;
		System.gc();
		
		Thread.sleep(500);
		if (SAVE_HOOK != null) {
			SAVE_HOOK.isAlive();
		} else {
			System.out.println("No, I am dead :(");
		}
		
		SAVE_HOOK = null;
		System.gc();
		
		Thread.sleep(500);
		if (SAVE_HOOK != null) {
			SAVE_HOOK.isAlive();
		} else {
			System.out.println("No, I am dead :(");
		}
	}
}
