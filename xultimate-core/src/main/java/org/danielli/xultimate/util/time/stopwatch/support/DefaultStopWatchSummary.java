package org.danielli.xultimate.util.time.stopwatch.support;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Comparator;

import org.danielli.xultimate.util.time.DurationFormatUtils;
import org.danielli.xultimate.util.time.stopwatch.StopWatch;
import org.danielli.xultimate.util.time.stopwatch.StopWatchSummary;
import org.danielli.xultimate.util.time.stopwatch.StopWatch.TaskInfo;
import org.slf4j.Logger;

/**
 * 默认汇总，显示秒表信息（秒表ID、开始时间、结束时间、运行时间、任务个数）和任务信息。任务支持排序，如果选择排序，按运行时间排序，显示的运行时间精度为ms。
 * 
 * @author Daniel Li
 * @since 17 Jun 2013
 */
public class DefaultStopWatchSummary implements StopWatchSummary {
	
	private Logger logger;
	private boolean sort;
	
	public DefaultStopWatchSummary(Logger logger, boolean sort) {
		this.logger = logger;
		this.sort = sort;
	}
	
	@Override
	public void summarize(StopWatch stopWatch) {
		if (logger.isDebugEnabled()) {
			logger.debug("StopWatch '{}': start timestamp(ns): {}, stop timestamp(ns): {}, running time = {}, task count: {}", new Object[] { stopWatch.getId(), stopWatch.getStartTime(), stopWatch.getStopTime(), DurationFormatUtils.formatDuration(stopWatch.getTotalTimeForMS(), SimpleStopWatchSummary.DATE_FORMAT), stopWatch.getTaskCount() }) ;
			if (stopWatch.getTaskCount() == 0) {
				logger.debug("No task info kept");
			} else {
				NumberFormat pf = NumberFormat.getPercentInstance();
				pf.setMinimumIntegerDigits(3);
				pf.setMinimumFractionDigits(5);
				pf.setGroupingUsed(false);
				TaskInfo[] taskInfos = stopWatch.getTaskInfo();
				if (sort) {
					Arrays.sort(taskInfos, new Comparator<TaskInfo>() {

						@Override
						public int compare(TaskInfo o1, TaskInfo o2) {
							return (int) (o2.getTotalTimeForNS() - o1.getTotalTimeForNS());
						}

					});
				}
				for (TaskInfo task : taskInfos) {
					logger.debug("\tTask Name '{}': start timestamp(ns): {}, stop timestamp(ns): {}, running time: {} ({})", new Object[] { task.getTaskName(), task.getStartTime(), task.getStopTime(), DurationFormatUtils.formatDuration(task.getTotalTimeForMS(), SimpleStopWatchSummary.DATE_FORMAT),  pf.format(task.getTotalTimeForNS() / (double) stopWatch.getTotalTimeForNS()) });
				}
			}
		}
	}

}
