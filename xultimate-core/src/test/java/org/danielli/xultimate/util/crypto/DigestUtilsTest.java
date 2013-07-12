package org.danielli.xultimate.util.crypto;

import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Assert;
import org.junit.Test;

public class DigestUtilsTest {
	
	@Test
	public void testMd5() {
		String source = "123456";
		PerformanceMonitor.start("DigestUtilsTest");
		
		Assert.assertEquals("e10adc3949ba59abbe56e057f20f883e", DigestUtils.digest(MessageDigestAlgorithms.MD5, source));
		PerformanceMonitor.mark("md5 Test");
		Assert.assertEquals("7c4a8d09ca3762af61e59520943dc26494f8941b", DigestUtils.digest(MessageDigestAlgorithms.SHA_1, source));
		PerformanceMonitor.mark("sha1 Test");
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(false));
		PerformanceMonitor.remove();
		
	}

}
