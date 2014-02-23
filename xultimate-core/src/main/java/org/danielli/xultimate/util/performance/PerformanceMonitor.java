package org.danielli.xultimate.util.performance;

import org.danielli.xultimate.util.time.stopwatch.StopWatch;
import org.danielli.xultimate.util.time.stopwatch.StopWatchContext;
import org.danielli.xultimate.util.time.stopwatch.support.AbstractStopWatchSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 性能监测工具。
 * 
 * @author Daniel Li
 * @since 16 Jun 2013
 */
public class PerformanceMonitor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceMonitor.class);
	
	/**
	 * 开始秒表计时。
	 */
	public static void start(String stopWatchId) {
		StopWatch stopWatch = new StopWatch(stopWatchId);
		StopWatchContext.set(stopWatch);
		LOGGER.trace("Begin [{}] Stopwatch", stopWatch.getId());
		stopWatch.start();
	}
	
	/**
	 * 结束秒表计时。
	 */
	public static void stop() {
		StopWatch stopWatch = StopWatchContext.get();
		LOGGER.trace("Stop [{}] Stopwatch", stopWatch.getId());
		stopWatch.stop();
	}
	
	/**
	 * 重置秒表计时。
	 */
	public static void reset() {
		StopWatch stopWatch = StopWatchContext.get();
		LOGGER.trace("Reset [{}] Stopwatch", stopWatch.getId());
		stopWatch.reset();
	}
	
	/**
	 * 暂停秒表计时。
	 */
	public static void suspend() {
		StopWatch stopWatch = StopWatchContext.get();
		LOGGER.trace("Suspend [{}] Stopwatch", stopWatch.getId());
		stopWatch.suspend();
	}
	
	/**
	 * 恢复秒表计时。
	 */
	public static void resume() {
		StopWatch stopWatch = StopWatchContext.get();
		LOGGER.trace("Resume [{}] Stopwatch", stopWatch.getId());
		stopWatch.resume();
	}
	
	/**
	 * 获取当前线程中的StopWatch。
	 */
	public static StopWatch get() {
		return StopWatchContext.get();
	}
	
	/**
	 * 移除当前线程中的StopWatch。
	 */
	public static void remove() {
		StopWatchContext.remove();
	}
	
	/**
	 * 标记任务。
	 */
	public static void mark(String taskName) {
		StopWatch stopWatch = StopWatchContext.get();
		LOGGER.trace("Add [{}] Task", taskName);
		stopWatch.mark(taskName);
	}
	
	/**
	 * 秒表汇总。
	 */
	public static void summarize(AbstractStopWatchSummary stopWatchSummary) {
		StopWatch stopWatch = StopWatchContext.get();
		LOGGER.trace("Print [{}] Stopwatch Info", stopWatch.getId());
		if (stopWatchSummary.getLogger() == null) {
			stopWatchSummary.setLogger(LOGGER);
		}
		stopWatchSummary.summarize(stopWatch);
	}
}
