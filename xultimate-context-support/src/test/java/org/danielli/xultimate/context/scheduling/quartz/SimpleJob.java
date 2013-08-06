package org.danielli.xultimate.context.scheduling.quartz;

import java.util.Date;
import java.util.Map.Entry;
import java.util.Set;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleJob implements Job {

	static Logger logger = LoggerFactory.getLogger(SimpleJob.class); 

	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDetail jobDetail = context.getJobDetail();
		String jobName = jobDetail.getKey().getName();
		
		try {
			logger.info(context.getScheduler().getSchedulerInstanceId() + " execute " + jobName + " fired at " + new Date());
		} catch (SchedulerException e) {
			e.printStackTrace();
		}  
		
		JobDataMap dataMap = jobDetail.getJobDataMap();
		
		Set<Entry<String, Object>> set = dataMap.entrySet();
		for (Entry<String, Object> entry : set) {
			logger.info(entry.getKey() + ":" + entry.getValue());  
		}

	}

}
