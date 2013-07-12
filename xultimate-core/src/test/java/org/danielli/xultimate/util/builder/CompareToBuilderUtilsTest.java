package org.danielli.xultimate.util.builder;

import java.util.Date;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.danielli.xultimate.util.builder.Employee;
import org.danielli.xultimate.util.builder.Manager;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompareToBuilderUtilsTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CompareToBuilderUtilsTest.class);
	
	@Test
	public void test1() {
		Date date = new Date();
		Employee employee1 = new Employee("Daniel Li", 20, date);
		Employee employee2 = new Employee("Daniel Li", 20, date);
		Manager manager1 = new Manager("Daniel Li", 20, date, 0);
		Manager manager2 = new Manager("Daniel Li", 20, date, 0) {
			
		};
		PerformanceMonitor.start("CompareToBuilderUtilsTest");
		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareByClass(employee1, employee2)));
		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareByClass(employee1, employee2)");

		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareByClass(employee2, employee1)));
		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareByClass(employee2, employee1)");

		LOGGER.info(String.valueOf(CompareToBuilder.reflectionCompare(employee1, employee2)));
		PerformanceMonitor.mark("CompareToBuilder.reflectionCompare(employee1, employee2)");
		

		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareByInstance(employee1, employee2)));
		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareByInstance(employee1, employee2)");

		LOGGER.info(String.valueOf(CompareToBuilder.reflectionCompare(employee2, employee1)));
		PerformanceMonitor.mark("CompareToBuilder.reflectionCompare(employee2, employee1)");

		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareByInstance(employee2, employee1)));
		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareByInstance(employee2, employee1)");

		
//		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareByClass(employee1, manager1)));
//		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareByClass(employee1, manager1)");

//		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareByClass(manager1, employee1)));
//		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareByClass(manager1, employee1)");

//		LOGGER.info(String.valueOf(CompareToBuilder.reflectionCompare(employee1, manager1)));
//		PerformanceMonitor.mark("CompareToBuilder.reflectionCompare(employee1, manager1)");

//		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareByInstance(employee1, manager1)));
//		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareByInstance(employee1, manager1)");
		
//		LOGGER.info(String.valueOf(CompareToBuilder.reflectionCompare(manager1, employee1)));
//		PerformanceMonitor.mark("CompareToBuilder.reflectionCompare(manager1, employee1)");

//		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareByInstance(manager1, employee1)));
//		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareByInstance(manager1, employee1)");

//		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareByClass(manager2, manager1)));
//		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareByClass(manager2, manager1)");

//		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareByClass(manager1, manager2)));
//		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareByClass(manager1, manager2)");

//		LOGGER.info(String.valueOf(CompareToBuilder.reflectionCompare(manager2, manager1)));
//		PerformanceMonitor.mark("CompareToBuilder.reflectionCompare(manager2, manager1)");

		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareByInstance(manager2, manager1)));
		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareByInstance(manager2, manager1)");

		LOGGER.info(String.valueOf(CompareToBuilder.reflectionCompare(manager1, manager2)));
		PerformanceMonitor.mark("CompareToBuilder.reflectionCompare(manager1, manager2)");

		LOGGER.info(String.valueOf(CompareToBuilderUtils.reflectionCompareByInstance(manager1, manager2)));
		PerformanceMonitor.mark("CompareToBuilderUtils.reflectionCompareByInstance(manager1, manager2)");
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(PerformanceMonitor.LOGGER, false));
		PerformanceMonitor.remove();
	}
}
