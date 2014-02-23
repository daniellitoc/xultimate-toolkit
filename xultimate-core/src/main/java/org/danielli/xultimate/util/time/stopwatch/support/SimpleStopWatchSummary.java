package org.danielli.xultimate.util.time.stopwatch.support;

import org.danielli.xultimate.util.time.DateUtils;
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
public class SimpleStopWatchSummary extends AbstractStopWatchSummary {

	@Override
	public void summarize(StopWatch stopWatch, Logger logger) {
		if (logger.isTraceEnabled()) {
			logger.trace("StopWatch '{}': start timestamp(ns): {}, stop timestamp(ns): {}, running time = {} ({} ns) ({} us) ({} ms), task count: {}", new Object[] { stopWatch.getId(), stopWatch.getStartTime(), stopWatch.getStopTime(), DurationFormatUtils.formatDuration(stopWatch.getTotalTime() / DateUtils.NANOS_PER_MILLIS, StopWatchSummary.DATE_FORMAT), stopWatch.getTotalTime(), stopWatch.getTotalTime() / DateUtils.NANOS_PER_MICROS, stopWatch.getTotalTime() / DateUtils.NANOS_PER_MILLIS, stopWatch.getTaskCount() }) ;
		}
	}
}
