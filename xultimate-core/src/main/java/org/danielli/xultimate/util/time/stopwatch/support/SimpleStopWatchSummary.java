package org.danielli.xultimate.util.time.stopwatch.support;

import org.danielli.xultimate.util.time.DurationFormatUtils;
import org.danielli.xultimate.util.time.stopwatch.StopWatch;
import org.danielli.xultimate.util.time.stopwatch.StopWatchSummary;
import org.slf4j.Logger;

/**
 * 简单汇总，只显示秒表信息（秒表ID、开始时间、结束时间、运行时间、任务个数）。
 * 
 * @author Daniel Li
 * @since 17 Jun 2013
 */
public class SimpleStopWatchSummary implements StopWatchSummary {

	public static final String DATE_FORMAT = "yyyy:MM:dd H:mm:ss.SSS";
	
	private Logger logger;
	
	public SimpleStopWatchSummary(Logger logger) {
		this.logger = logger;
	}
	
	@Override
	public void summarize(StopWatch stopWatch) {
		if (logger.isDebugEnabled()) {
			logger.debug("StopWatch '{}': start timestamp(ns): {}, stop timestamp(ns): {}, running time = {}, task count: {}", new Object[] { stopWatch.getId(), stopWatch.getStartTime(), stopWatch.getStopTime(), DurationFormatUtils.formatDuration(stopWatch.getTotalTimeForMS(), DATE_FORMAT), stopWatch.getTaskCount() }) ;
		}
	}

}
