package org.danielli.xultimate.context.scheduling.quartz;

import javax.annotation.Resource;

import org.danielli.xultimate.context.scheduling.quartz.util.SchedulerUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-service-config.xml", "classpath:/applicationContext-service-crypto.xml", "classpath:/applicationContext-dao-base.xml", "classpath:/quartz/applicationContext-dao-quartz.xml", "classpath:/quartz/applicationContext-service-quartz.xml" })
public class SchedulerTest {
	
	@Resource
	private Scheduler quartzClusteredScheduler;
	
	@Resource
	private Scheduler quartzClusteredSchedulerTest;
	
//	@Resource
	private Scheduler quartzNonClusteredScheduler;
	
//	@Resource
	@SuppressWarnings("unused")
	private Scheduler quartzNonClusteredSchedulerTest;
	
	@Test
	public void testClusteredScheduler() {
		try {
			JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity(new JobKey("ScanDirectory", Scheduler.DEFAULT_GROUP)).storeDurably(true).build();
			jobDetail.getJobDataMap().put("SCAN_DIR", "/home/toc");
			SchedulerUtils.addJob(quartzClusteredScheduler, jobDetail, false);
			
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("ScanTrigger", Scheduler.DEFAULT_GROUP).startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).withRepeatCount(1000)).forJob(jobDetail).build();
			SchedulerUtils.scheduleJob(quartzClusteredScheduler, trigger, false);
			
			Trigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("CronScanTrigger", Scheduler.DEFAULT_GROUP).startNow().withSchedule(CronScheduleBuilder.cronSchedule("0 0 12 * * ?")).forJob(jobDetail).build();
			SchedulerUtils.scheduleJob(quartzClusteredScheduler, cronTrigger, false);
			
			Thread.sleep(15000);
			quartzClusteredScheduler.shutdown();
			Thread.sleep(15000);
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void testNonClusteredScheduler() {
		try {
			JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity(new JobKey("ScanDirectory", Scheduler.DEFAULT_GROUP)).storeDurably(true).build();
			jobDetail.getJobDataMap().put("SCAN_DIR", "/home/toc");
			SchedulerUtils.addJob(quartzNonClusteredScheduler, jobDetail, false);
			
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("ScanTrigger", Scheduler.DEFAULT_GROUP).startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).withRepeatCount(1000)).forJob(jobDetail).build();
			SchedulerUtils.scheduleJob(quartzNonClusteredScheduler, trigger, false);
			
			Trigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("CronScanTrigger", Scheduler.DEFAULT_GROUP).startNow().withSchedule(CronScheduleBuilder.cronSchedule("0 0 12 * * ?")).forJob(jobDetail).build();
			SchedulerUtils.scheduleJob(quartzNonClusteredScheduler, cronTrigger, false);
			
			Thread.sleep(15000);
			quartzNonClusteredScheduler.shutdown();
			Thread.sleep(15000);
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
//	public void test1() {
//		try {
//			if (!quartzNonClusteredScheduler.isInStandbyMode()) {
//				quartzNonClusteredScheduler.standby();
//			}
//			
//			quartzNonClusteredScheduler.start();
//			
//			JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity(new JobKey("ScanDirectory", Scheduler.DEFAULT_GROUP)).build();
//			jobDetail.getJobDataMap().put("SCAN_DIR", "/home/toc");
//			
//			Trigger trigger = TriggerBuilder.newTrigger().withIdentity("ScanTrigger", Scheduler.DEFAULT_GROUP).startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).withRepeatCount(1000)).build();
//			
//			quartzNonClusteredScheduler.scheduleJob(jobDetail, trigger);
////			TriggerBuilder.newTrigger().withPriority(CronScheduleBuilder.cronSchedule(""));
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
//	}
}
