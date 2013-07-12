package org.danielli.xultimate.util.builder;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.danielli.xultimate.util.builder.Employee;
import org.danielli.xultimate.util.builder.Manager;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.joda.time.DateTime;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EqualsBuilderUtilsTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EqualsBuilderUtilsTest.class);
	
	@Test
	public void test1() {
		DateTime date = new DateTime();
		Employee employee1 = new Employee("Daniel Li", 20, date);
		Employee employee2 = new Employee("李天棚", 20, date);
		Manager manager1 = new Manager("Daniel Li", 20, date, 0);
		Manager manager2 = new Manager("Daniel Li", 20, date, 0) {
			
		};
		
		PerformanceMonitor.start("EqualsBuilderUtilsTest");
		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsForBothClass(employee1, employee2)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsForBothClass(employee1, employee2)");
		
		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsForBothClass(employee2, employee1)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsForBothClass(employee2, employee1)");
		
		
		LOGGER.info(String.valueOf(EqualsBuilder.reflectionEquals(employee1, employee2, false)));
		PerformanceMonitor.mark("EqualsBuilder.reflectionEquals(employee1, employee2, false)");
		
		LOGGER.info(String.valueOf(EqualsBuilder.reflectionEquals(employee2, employee1, false)));
		PerformanceMonitor.mark("EqualsBuilder.reflectionEquals(employee2, employee1, false)");
		
		
		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsForLeftClass(employee1, employee2)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsForLeftClass(employee1, employee2)");
		
		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsForLeftClass(employee2, employee1)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsForLeftClass(employee2, employee1)");
		
		
		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsForBothClass(employee1, manager1)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsForBothClass(employee1, manager1)");
	
		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsForBothClass(manager1, employee1)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsForBothClass(manager1, employee1)");

		
		LOGGER.info(String.valueOf(EqualsBuilder.reflectionEquals(employee1, manager1, false)));
		PerformanceMonitor.mark("EqualsBuilder.reflectionEquals(employee1, manager1, false)");
		
		LOGGER.info(String.valueOf(EqualsBuilder.reflectionEquals(manager1, employee1, false)));
		PerformanceMonitor.mark("EqualsBuilder.reflectionEquals(manager1, employee1, false)");
		
		
		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsForLeftClass(employee1, manager1)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsForLeftClass(employee1, manager1)");
		
		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsForLeftClass(manager1, employee1)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsForLeftClass(manager1, employee1)");
		
		
		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsForBothClass(manager2, manager1)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsForBothClass(manager2, manager1)");
		
		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsForBothClass(manager1, manager2)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsForBothClass(manager1, manager2)");
		
		LOGGER.info(String.valueOf(EqualsBuilder.reflectionEquals(manager2, manager1, false)));
		PerformanceMonitor.mark("EqualsBuilder.reflectionEquals(manager2, manager1, false)");
		
		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsForLeftClass(manager2, manager1)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsForLeftClass(manager2, manager1)");
		
		LOGGER.info(String.valueOf(EqualsBuilder.reflectionEquals(manager1, manager2, false)));
		PerformanceMonitor.mark("EqualsBuilder.reflectionEquals(manager1, manager2, false)");
		
		LOGGER.info(String.valueOf(EqualsBuilderUtils.reflectionEqualsForLeftClass(manager1, manager2)));
		PerformanceMonitor.mark("EqualsBuilderUtils.reflectionEqualsForLeftClass(manager1, manager2)");
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(false));
		PerformanceMonitor.remove();
	}
}
