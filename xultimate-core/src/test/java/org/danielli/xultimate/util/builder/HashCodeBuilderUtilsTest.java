package org.danielli.xultimate.util.builder;

import java.util.Date;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.danielli.xultimate.util.builder.Employee;
import org.danielli.xultimate.util.builder.Manager;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HashCodeBuilderUtilsTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HashCodeBuilderUtilsTest.class);
	
	@Test
	public void test1() {
		Date date = new Date();
		Employee employee1 = new Employee("Daniel Li", 20, date);
		Employee employee2 = new Employee("Daniel Li", 20, date);
		Manager manager1 = new Manager("Daniel Li", 20, date, 0);
		Manager manager2 = new Manager("Daniel Li", 20, date, 0) {
			
		};
		
		PerformanceMonitor.start("HashCodeBuilderUtilsTest");
		
		LOGGER.info(String.valueOf(HashCodeBuilder.reflectionHashCode(employee1)));
		PerformanceMonitor.mark("HashCodeBuilder.reflectionHashCode(employee1)");
		
		
		LOGGER.info(String.valueOf(HashCodeBuilderUtils.reflectionHashCode(employee1)));
		PerformanceMonitor.mark("HashCodeBuilderUtils.reflectionHashCode(employee1)");
		
		
		LOGGER.info(String.valueOf(HashCodeBuilderUtils.reflectionHashCode(employee2)));
		PerformanceMonitor.mark("HashCodeBuilderUtils.reflectionHashCode(employee2)");
		
		
		LOGGER.info(String.valueOf(HashCodeBuilder.reflectionHashCode(employee2)));
		PerformanceMonitor.mark("HashCodeBuilder.reflectionHashCode(employee2)");
		
		
		LOGGER.info(String.valueOf(HashCodeBuilder.reflectionHashCode(manager1)));
		PerformanceMonitor.mark("HashCodeBuilder.reflectionHashCode(manager1)");
		
		
		LOGGER.info(String.valueOf(HashCodeBuilderUtils.reflectionHashCode(manager1)));
		PerformanceMonitor.mark("HashCodeBuilderUtils.reflectionHashCode(manager1)");
		
		
		LOGGER.info(String.valueOf(HashCodeBuilder.reflectionHashCode(manager2)));
		PerformanceMonitor.mark("HashCodeBuilder.reflectionHashCode(manager2)");
		
		
		LOGGER.info(String.valueOf(HashCodeBuilderUtils.reflectionHashCode(manager2)));
		PerformanceMonitor.mark("HashCodeBuilderUtils.reflectionHashCode(manager2)");
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(PerformanceMonitor.LOGGER, false));
		PerformanceMonitor.remove();
	}
}
