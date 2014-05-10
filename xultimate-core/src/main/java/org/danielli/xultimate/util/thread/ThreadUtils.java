package org.danielli.xultimate.util.thread;

public class ThreadUtils {

	/**
	 * 当前线程等待，直到活跃线程数小于等于activeThreadCount。
	 * @param activeThreadCount 目标活跃线程数目。
	 */
	public static void waitUntilLe(int activeThreadCount) {
		while (Thread.activeCount() > activeThreadCount) {
			try {
				Thread.sleep(1 * 1000);
			} catch (InterruptedException e) {
				return;
			}
		}
	}
	
}
