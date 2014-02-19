package org.danielli.xultimate.util.time.stopwatch;

/**
 * 秒表上下文。
 * 
 * @author Daniel Li
 * @since 16 Jun 2013
 */
public class StopWatchContext {

	private static ThreadLocal<StopWatch> stopWatchThreadLocal = new ThreadLocal<>();
	
	public static StopWatch get() {
		return stopWatchThreadLocal.get();
	}
	
	public static void remove() {
		stopWatchThreadLocal.remove();
	}
	
	public static void set(StopWatch stopWatch) {
		stopWatchThreadLocal.set(stopWatch);
	}
}
