package org.danielli.xultimate.context.scheduling.quartz.util;

import org.quartz.JobDetail;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

/**
 * 调度器工具。
 * 
 * @author Daniel Li
 * @since 18 Jun 2013
 */
public class SchedulerUtils {
	
	/**
	 * 添加任务。
	 * 
	 * @param scheduler 调度器。
	 * @param jobDetail 任务。
	 * @param overwriteExistingJobs 覆盖存在的任务。
	 * @return 添加成功返回true，否则返回false。
	 * @throws SchedulerException 调度器异常。
	 */
	public static boolean addJob(Scheduler scheduler, JobDetail jobDetail, boolean overwriteExistingJobs) throws SchedulerException {
		if (overwriteExistingJobs || !checkExists(scheduler, jobDetail)) {
			scheduler.addJob(jobDetail, true);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 执行任务。
	 * 
	 * @param scheduler 调度器。
	 * @param trigger 触发器。
	 * @param overwriteExistingJobs 覆盖存在的触发器。
	 * @return 添加成功返回true，否则返回false。
	 * @throws SchedulerException 调度器异常。
	 */
	public static boolean scheduleJob(Scheduler scheduler, Trigger trigger, boolean overwriteExistingJobs) throws SchedulerException {
		boolean triggerExists = checkExists(scheduler, trigger);
		if (!triggerExists || overwriteExistingJobs) {
			if (!triggerExists) {
				try {
					scheduler.scheduleJob(trigger);
				}
				catch (ObjectAlreadyExistsException ex) {
					if (overwriteExistingJobs) {
						rescheduleJob(scheduler, trigger);
					}
				}
			}
			else {
				rescheduleJob(scheduler, trigger);
			}
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 检测任务是否存在。
	 * 
	 * @param scheduler 调度器。
	 * @param jobDetail 任务。
	 * @return 如果存在返回true，否则返回false。
	 * @throws SchedulerException 调度器异常。
	 */
	public static boolean checkExists(Scheduler scheduler, JobDetail jobDetail) throws SchedulerException {
		return scheduler.checkExists(jobDetail.getKey());
	}
	
	/**
	 * 检测触发器是否存在。
	 * 
	 * @param scheduler 调度器。
	 * @param trigger 触发器。
	 * @return 如果存在返回true，否则返回false。
	 * @throws SchedulerException 调度器异常。
	 */
	public static boolean checkExists(Scheduler scheduler, Trigger trigger) throws SchedulerException {
		return scheduler.checkExists(trigger.getKey());
	}
	
	/**
	 * 获取任务。
	 * 
	 * @param scheduler 调度器。
	 * @param trigger 触发器。
	 * @return 任务。
	 * @throws SchedulerException 调度器异常。
	 */
	public static JobDetail getJobDetail(Scheduler scheduler, Trigger trigger) throws SchedulerException {
		return scheduler.getJobDetail(trigger.getJobKey());
	}
	
	/**
	 * 重新执行任务。
	 * 
	 * @param scheduler 调度器。
	 * @param trigger 触发器。
	 * @throws SchedulerException 调度器异常。
	 */
	public static void rescheduleJob(Scheduler scheduler, Trigger trigger) throws SchedulerException {
		scheduler.rescheduleJob(trigger.getKey(), trigger);
	}
}
