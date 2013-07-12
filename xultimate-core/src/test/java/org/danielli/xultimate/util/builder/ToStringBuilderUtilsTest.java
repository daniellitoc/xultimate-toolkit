package org.danielli.xultimate.util.builder;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToStringBuilderUtilsTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ToStringBuilderUtilsTest.class);
	
	@Test
	public void test1() {
		Date date = new Date();
		Employee employee1 = new Employee("Daniel Li", 20, date);
		Employee employee2 = new Employee("Daniel Li", 20, date);
		Manager manager1 = new Manager("Daniel Li", 20, date, 0);
		Manager manager2 = new Manager("Daniel Li", 20, date, 0) {
			
		};
		
		PerformanceMonitor.start("ToStringBuilderUtilsTest");
		
		LOGGER.info(String.valueOf(ToStringBuilder.reflectionToString(employee1)));
		PerformanceMonitor.mark("ToStringBuilder.reflectionToString(employee1)");
		
		LOGGER.info(String.valueOf(ToStringBuilderUtils.reflectionToString(employee1)));
		PerformanceMonitor.mark("ToStringBuilderUtils.reflectionToString(employee1)");
	
		LOGGER.info(String.valueOf(ToStringBuilder.reflectionToString(employee2)));
		PerformanceMonitor.mark("ToStringBuilder.reflectionToString(employee2)");
	
		LOGGER.info(String.valueOf(ToStringBuilderUtils.reflectionToString(employee2)));
		PerformanceMonitor.mark("ToStringBuilderUtils.reflectionToString(employee2)");

		LOGGER.info(String.valueOf(ToStringBuilder.reflectionToString(manager1)));
		PerformanceMonitor.mark("ToStringBuilder.reflectionToString(manager1)");

		LOGGER.info(String.valueOf(ToStringBuilderUtils.reflectionToString(manager1)));
		PerformanceMonitor.mark("ToStringBuilderUtils.reflectionToString(manager1)");

		LOGGER.info(String.valueOf(ToStringBuilder.reflectionToString(manager2)));
		PerformanceMonitor.mark("ToStringBuilder.reflectionToString(manager2)");

		LOGGER.info(String.valueOf(ToStringBuilderUtils.reflectionToString(manager2)));
		PerformanceMonitor.mark("ToStringBuilderUtils.reflectionToString(manager2)");
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(PerformanceMonitor.LOGGER, false));
		PerformanceMonitor.remove();
	}
}
