package org.danielli.xultimate.test.thread;

/**
 * 根据{@code com.oldratlee.fucking.concurrency.InvalidLongDemo}进行的修改。
 * @author ding.lid
 * @author Daniel Li
 * @since 2014.06.17
 */
public class LongCase {
	private long value = 0;

    public class SetLongRunnable implements Runnable {
        @Override
        public void run() {
            for (long value = 0; ; value++) {
            	long tmpValue = value << 32 | value;
            	LongCase.this.value = tmpValue;
            }
        }
    }
    
    public class CheckLongRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                long tmpValue = value;
                long high = tmpValue >>> 32;
                long low = tmpValue & 0xFFFFFFFFl;
                if (high != low) {
                	System.out.printf("Source/Hight/Low: %s/%s/%s\n", tmpValue, high, low);
                } else {
                	Thread.yield();
                }
            }
        }
    }
	
    public static void main(String[] args) {
    	LongCase longTest = new LongCase();
    	new Thread(longTest.new SetLongRunnable()).start();
    	new Thread(longTest.new CheckLongRunnable()).start();
    }
}
