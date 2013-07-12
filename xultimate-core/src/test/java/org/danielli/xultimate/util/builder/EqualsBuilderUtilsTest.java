package org.danielli.xultimate.util.builder;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.danielli.xultimate.util.builder.Employee;
import org.danielli.xultimate.util.builder.Manager;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EqualsBuilderUtilsTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EqualsBuilderUtilsTest.class);
	
	@Test
	public void test1() {
		Date date = new Date();
		Employee employee1 = new Employee("Daniel Li", 20, date);
		Employee employee2 = new Employee("Daniel Li", 20, date);
		Manager manager1 = new Manager("Daniel Li", 20, date, 0);
		Manager manager2 = new Manager("Daniel Li", 20, date, 0) {
			
		};
		
		PerformanceMonitor.start("EqualsBuilderUtilsTest");
		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsByClass(employee1, employee2)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsByClass(employee1, employee2)");
		
		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsByClass(employee2, employee1)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsByClass(employee2, employee1)");
		
		LOGGER.info(String.valueOf(EqualsBuilder.reflectionEquals(employee1, employee2, false)));
		PerformanceMonitor.mark("EqualsBuilder.reflectionEquals(employee1, employee2, false)");
		
		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsByInstance(employee1, employee2)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsByInstance(employee1, employee2)");
		
		LOGGER.info(String.valueOf(EqualsBuilder.reflectionEquals(employee2, employee1, false)));
		PerformanceMonitor.mark("EqualsBuilder.reflectionEquals(employee2, employee1, false)");

		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsByInstance(employee2, employee1)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsByInstance(employee2, employee1)");
		
		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsByClass(employee1, manager1)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsByClass(employee1, manager1)");
	
		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsByClass(manager1, employee1)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsByClass(manager1, employee1)");

		LOGGER.info(String.valueOf(EqualsBuilder.reflectionEquals(employee1, manager1, false)));
		PerformanceMonitor.mark("EqualsBuilder.reflectionEquals(employee1, manager1, false)");
		
		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsByInstance(employee1, manager1)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsByInstance(employee1, manager1)");

		LOGGER.info(String.valueOf(EqualsBuilder.reflectionEquals(manager1, employee1, false)));
		PerformanceMonitor.mark("EqualsBuilder.reflectionEquals(manager1, employee1, false)");
		
		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsByInstance(manager1, employee1)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsByInstance(manager1, employee1)");
		
		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsByClass(manager2, manager1)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsByClass(manager2, manager1)");
		
		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsByClass(manager1, manager2)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsByClass(manager1, manager2)");
		
		LOGGER.info(String.valueOf(EqualsBuilder.reflectionEquals(manager2, manager1, false)));
		PerformanceMonitor.mark("EqualsBuilder.reflectionEquals(manager2, manager1, false)");
		
		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsByInstance(manager2, manager1)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsByInstance(manager2, manager1)");
		
		LOGGER.info(String.valueOf(EqualsBuilder.reflectionEquals(manager1, manager2, false)));
		PerformanceMonitor.mark("EqualsBuilder.reflectionEquals(manager1, manager2, false)");
		
		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsByInstance(manager1, manager2)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsByInstance(manager1, manager2)");
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(PerformanceMonitor.LOGGER, false));
		PerformanceMonitor.remove();
	}
}
