package org.danielli.xultimate.util.builder;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.danielli.xultimate.util.builder.Employee;
import org.danielli.xultimate.util.builder.Manager;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.joda.time.DateTime;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompareToBuilderUtilsTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CompareToBuilderUtilsTest.class);
	
	@Test
	public void test1() {
		DateTime date = new DateTime();
		Employee employee1 = new Employee("Daniel Li", 20, date);
		Employee employee2 = new Employee("李天棚", 20, date);
		Manager manager1 = new Manager("Daniel Li", 20, date, 0);
		Manager manager2 = new Manager("Daniel Li", 20, date, 0) {
			
		};
		PerformanceMonitor.start("CompareToBuilderUtilsTest");
		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareForBothClass(employee1, employee2)));
		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareForBothClass(employee1, employee2)");

		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareForBothClass(employee2, employee1)));
		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareForBothClass(employee2, employee1)");

		LOGGER.info(String.valueOf(CompareToBuilder.reflectionCompare(employee1, employee2)));
		PerformanceMonitor.mark("CompareToBuilder.reflectionCompare(employee1, employee2)");
		
		LOGGER.info(String.valueOf(CompareToBuilder.reflectionCompare(employee2, employee1)));
		PerformanceMonitor.mark("CompareToBuilder.reflectionCompare(employee2, employee1)");

		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareForLeftClass(employee1, employee2)));
		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareForLeftClass(employee1, employee2)");

		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareForLeftClass(employee2, employee1)));
		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareForLeftClass(employee2, employee1)");
		
//		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareForBothClass(employee1, manager1)));
//		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareForBothClass(employee1, manager1)");
//
//		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareForBothClass(manager1, employee1)));
//		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareForBothClass(manager1, employee1)");

		LOGGER.info(String.valueOf(CompareToBuilder.reflectionCompare(employee1, manager1)));
		PerformanceMonitor.mark("CompareToBuilder.reflectionCompare(employee1, manager1)");

		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareForLeftClass(employee1, manager1)));
		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareForLeftClass(employee1, manager1)");
		
//		LOGGER.info(String.valueOf(CompareToBuilder.reflectionCompare(manager1, employee1)));
//		PerformanceMonitor.mark("CompareToBuilder.reflectionCompare(manager1, employee1)");
//
//		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareForLeftClass(manager1, employee1)));
//		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareForLeftClass(manager1, employee1)");

//		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareForBothClass(manager2, manager1)));
//		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareForBothClass(manager2, manager1)");
//
//		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareForBothClass(manager1, manager2)));
//		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareForBothClass(manager1, manager2)");

//		LOGGER.info(String.valueOf(CompareToBuilder.reflectionCompare(manager2, manager1)));
//		PerformanceMonitor.mark("CompareToBuilder.reflectionCompare(manager2, manager1)");
//
//		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareForLeftClass(manager2, manager1)));
//		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareForLeftClass(manager2, manager1)");

		LOGGER.info(String.valueOf(CompareToBuilder.reflectionCompare(manager1, manager2)));
		PerformanceMonitor.mark("CompareToBuilder.reflectionCompare(manager1, manager2)");

		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareForLeftClass(manager1, manager2)));
		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareForLeftClass(manager1, manager2)");
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(false));
		PerformanceMonitor.remove();
	}
}
