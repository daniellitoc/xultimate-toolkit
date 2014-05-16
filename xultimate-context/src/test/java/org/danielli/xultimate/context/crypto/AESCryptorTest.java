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
public class AESCryptorTest {
	
	@Resource(name = "stringStringAESCryptor")
	private Encryptor<String, String> encryptor;
	@Resource(name = "stringStringAESCryptor")
	private Decryptor<String, String> decryptor;
	
	@Resource(name = "pathStringStringAESCryptor")
	private Encryptor<String, String> pathStringStringAESEncryptor;
	@Resource(name = "pathStringStringAESCryptor")
	private Decryptor<String, String> pathStringStringAESDecryptor;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AESCryptorTest.class);

//	@Test
	public void test() {
		String source = "ultimate";
		PerformanceMonitor.start("AESCryptorTest");
		LOGGER.info("{}: {}", new Object[] { source, encryptor.encrypt(source) });
		PerformanceMonitor.mark("Encryptor.encrypt()");
		source = "dcbe8df70f8da53e3a323ccf7e9ed758";
		LOGGER.info("{}: {}", new Object[] { source, decryptor.decrypt(source) });
		PerformanceMonitor.mark("Decryptor.decrypt()");
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(false));
		PerformanceMonitor.remove();
	}
	
	@Test
	public void testPath() {
		String source = "ultimate";
		PerformanceMonitor.start("AESCryptorTest");
		LOGGER.info("{}: {}", new Object[] { source, pathStringStringAESEncryptor.encrypt(source) });
		PerformanceMonitor.mark("Encryptor.encrypt()");
		source = pathStringStringAESEncryptor.encrypt(source);
		LOGGER.info("{}: {}", new Object[] { source, pathStringStringAESDecryptor.decrypt(source) });
		PerformanceMonitor.mark("Decryptor.decrypt()");
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(false));
		PerformanceMonitor.remove();
	}
}
