package org.danielli.xultimate.util.time.stopwatch.support;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.collections.comparators.ComparableComparator;
import org.danielli.xultimate.util.time.DateUtils;
import org.danielli.xultimate.util.time.DurationFormatUtils;
import org.danielli.xultimate.util.time.stopwatch.StopWatch;
import org.danielli.xultimate.util.time.stopwatch.StopWatch.TaskInfo;
import org.slf4j.Logger;

/**
 * 高级汇总，显示秒表信息（秒表ID、开始时间、结束时间、运行时间、任务个数）和任务信息。任务支持排序，如果选择排序，按运行时间排序，显示的运行时间精度为ns。
 * 
 * @author Daniel Li
 * @since 17 Jun 2013
 */
public class AdvancedStopWatchSummary extends AbstractStopWatchSummary {

	private boolean sort;
	
	public AdvancedStopWatchSummary(boolean sort) {
		this.sort = sort;
	}
	
	private static NumberFormat pf = NumberFormat.getPercentInstance();
	static {
		pf.setMinimumIntegerDigits(3);
		pf.setMinimumFractionDigits(5);
		pf.setGroupingUsed(false);
	}

	@Override
	public void summarize(StopWatch stopWatch, Logger logger) {
		if (logger.isTraceEnabled()) {
			logger.trace("StopWatch '{}': start timestamp(ns): {}, stop timestamp(ns): {}, running time = {} ({} ns), task count: {}", new Object[] { stopWatch.getId(), stopWatch.getStartTime(), stopWatch.getStopTime(), DurationFormatUtils.formatDuration(stopWatch.getTotalTime() / DateUtils.NANOS_PER_MILLIS, SimpleStopWatchSummary.DATE_FORMAT), stopWatch.getTotalTime(), stopWatch.getTaskCount() }) ;
			if (stopWatch.getTaskCount() == 0) {
				logger.trace("No task info kept");
			} else {
				
				TaskInfo[] taskInfos = stopWatch.getTaskInfo();
				if (sort) {
					Arrays.sort(taskInfos, new Comparator<TaskInfo>() {

						@Override
						public int compare(TaskInfo o1, TaskInfo o2) {
							return new ComparableComparator().compare(o2.getTotalTime(), o1.getTotalTime());
						}

					});
				}
				for (TaskInfo task : taskInfos) {
					logger.trace("\tTask Name '{}': start timestamp(ns): {}, stop timestamp(ns): {}, running time: {} ({} ns) ({})", new Object[] { task.getTaskName(), task.getStartTime(), task.getStopTime(), DurationFormatUtils.formatDuration(task.getTotalTime() / DateUtils.NANOS_PER_MILLIS, SimpleStopWatchSummary.DATE_FORMAT), task.getTotalTime(), pf.format(task.getTotalTime() / (double) stopWatch.getTotalTime()) });
				}
			}
		}
	}
}
