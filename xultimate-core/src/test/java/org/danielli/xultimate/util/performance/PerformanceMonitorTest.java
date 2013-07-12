package org.danielli.xultimate.util.performance;

import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerformanceMonitorTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceMonitorTest.class);
	
	@Test
	public void test() {
		PerformanceMonitor.start("PerformanceMonitorTest");
		LOGGER.trace("Trace Message");
		LOGGER.trace("Trace Message {} {}{}", new Object[] { "Hello", "World", "!" });
		PerformanceMonitor.mark("testTraceString");
		if (LOGGER.isTraceEnabled()) {
			LOGGER.trace("Trace Message");
			LOGGER.trace("Trace Message {} {}{}", new Object[] { "Hello", "World", "!" });
		}
		PerformanceMonitor.mark("testTraceStringWithIf");
		LOGGER.debug("Debug Message");
		LOGGER.debug("Debug Message {} {}{}", new Object[] { "Hello", "World", "!" });
		PerformanceMonitor.mark("testDebugString");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Debug Message");
			LOGGER.debug("Debug Message {} {}{}", new Object[] { "Hello", "World", "!" });
		}
		PerformanceMonitor.mark("testDebugStringWithIf");
		LOGGER.error("Error Message");
		LOGGER.error("Error Message {} {}{}", new Object[] { "Hello", "World", "!" });
		PerformanceMonitor.mark("testErrorString");
		if (LOGGER.isErrorEnabled()) {
			LOGGER.error("Error Message");
			LOGGER.error("Error Message {} {}{}", new Object[] { "Hello", "World", "!" });
		}
		PerformanceMonitor.mark("testErrorStringWithIf");
		LOGGER.info("Info Message");
		LOGGER.info("Info Message {} {}{}", new Object[] { "Hello", "World", "!" });
		PerformanceMonitor.mark("testInfoString");
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Info Message");
			LOGGER.info("Info Message {} {}{}", new Object[] { "Hello", "World", "!" });
		}
		PerformanceMonitor.mark("testInfoStringWithIf");
		LOGGER.warn("Warn Message");
		LOGGER.warn("Warn Message {} {}{}", new Object[] { "Hello", "World", "!" });
		PerformanceMonitor.mark("testWarnString");
		if (LOGGER.isWarnEnabled()) {
			LOGGER.warn("Warn Message");
			LOGGER.warn("Warn Message {} {}{}", new Object[] { "Hello", "World", "!" });
		}
		PerformanceMonitor.mark("testWarnStringWithIf");
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(PerformanceMonitor.LOGGER, true));
		PerformanceMonitor.remove();
	}

}
