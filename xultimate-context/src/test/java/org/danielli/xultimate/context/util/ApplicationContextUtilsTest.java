package org.danielli.xultimate.context.util;

import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext-service-util.xml" })
public class ApplicationContextUtilsTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationContextUtilsTest.class);
	
	@Test
	public void test() {
		PerformanceMonitor.start("ApplicationContextUtilsTest");
		LOGGER.info(ApplicationContextUtils.getApplicationContext().toString());
		PerformanceMonitor.mark("ApplicationContextUtils.getApplicationContext()");
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(false));
		PerformanceMonitor.remove();
	}
}
