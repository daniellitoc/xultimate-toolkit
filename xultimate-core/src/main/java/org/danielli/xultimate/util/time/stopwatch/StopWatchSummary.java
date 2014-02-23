package org.danielli.xultimate.util.time.stopwatch;

/**
 * 秒表汇总器。
 * 
 * @author Daniel Li
 * @since 17 Jun 2013
 */
public interface StopWatchSummary {
	
	public String DATE_FORMAT = "yyyy:MM:dd H:mm:ss.SSS";
	
	/**
	 * 汇总指定秒表。
	 * 
	 * @param stopWatch 秒表。
	 */
	void summarize(StopWatch stopWatch);
}
