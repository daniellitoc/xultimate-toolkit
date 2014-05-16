package org.danielli.xultimate.context.crypto;

import javax.annotation.Resource;

import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-service-crypto.xml" })
public class RSACryptorTest {
	
	@Resource(name = "stringStringRSACryptor")
	private Encryptor<String, String> encryptor;
	@Resource(name = "stringStringRSACryptor")
	private Decryptor<String, String> decryptor;
	
	@Resource(name = "pathStringStringRSACryptor")
	private Encryptor<String, String> pathStringStringRSAEncryptor;
	@Resource(name = "pathStringStringRSACryptor")
	private Decryptor<String, String> pathStringStringRSADecryptor;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RSACryptorTest.class);

//	@Test
	public void test() {
		String source = "ultimate";
		PerformanceMonitor.start("RSACryptorTest");
		String result = encryptor.encrypt(source);
		LOGGER.info("{}: {}", new Object[] { source, result });
		PerformanceMonitor.mark("Encryptor.encrypt()");
		LOGGER.info("{}: {}", new Object[] { result, decryptor.decrypt(result) });
		PerformanceMonitor.mark("Decryptor.decrypt()");
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(false));
		PerformanceMonitor.remove();
	}
	
	@Test
	public void testPath() {
		String source = "ultimate";
		PerformanceMonitor.start("RSACryptorTest");
		String result = pathStringStringRSAEncryptor.encrypt(source);
		LOGGER.info("{}: {}", new Object[] { source, result });
		LOGGER.info("{}: {}", new Object[] { source, pathStringStringRSAEncryptor.encrypt(source) });
		PerformanceMonitor.mark("Encryptor.encrypt()");
		LOGGER.info("{}: {}", new Object[] { result, pathStringStringRSADecryptor.decrypt(result) });
		PerformanceMonitor.mark("Decryptor.decrypt()");
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(false));
		PerformanceMonitor.remove();
	}
}
