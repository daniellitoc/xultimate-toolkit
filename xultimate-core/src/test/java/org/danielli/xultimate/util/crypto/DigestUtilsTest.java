package org.danielli.xultimate.util.crypto;

import static org.junit.Assert.*;

import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DigestUtilsTest {

private static final Logger LOGGER = LoggerFactory.getLogger(DigestUtilsTest.class);
	
	@Test
	public void testMd5() {
		String source = "123456";
		PerformanceMonitor.start("DigestUtilsTest");
		
		LOGGER.info("{}: {}", new Object[] { source, DigestUtils.digest(MessageDigestAlgorithms.MD5, source) });
		PerformanceMonitor.mark("md5 Test");
		LOGGER.info("{}: {}", new Object[] { source, DigestUtils.digest(MessageDigestAlgorithms.SHA_1, source) });
		PerformanceMonitor.mark("sha1 Test");
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(PerformanceMonitor.LOGGER, false));
		PerformanceMonitor.remove();
		
	}

}
