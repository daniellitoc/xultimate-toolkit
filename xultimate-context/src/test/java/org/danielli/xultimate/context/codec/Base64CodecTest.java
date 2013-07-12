package org.danielli.xultimate.context.codec;

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
@ContextConfiguration(locations = { "classpath*:/applicationContext-service-codec.xml" })
public class Base64CodecTest {
	
	@Resource(name = "stringStringBase64Codec")
	private Encoder<String, String> encoder;
	@Resource(name = "stringStringBase64Codec")
	private Decoder<String, String> decoder;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Base64CodecTest.class);
	
	@Test
	public void test() {
		String source = "name=danielli";
		PerformanceMonitor.start("Base64CodecTest");
		LOGGER.info("{}: {}", new Object[] { source, encoder.encode(source) });
		PerformanceMonitor.mark("Base64Codec.encode()");
		source = "bmFtZT1kYW5pZWxsaQ==";
		LOGGER.info("{}: {}", new Object[] { source, decoder.decode(source) });
		PerformanceMonitor.mark("Base64Codec.encode()");
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(false));
		PerformanceMonitor.remove();
	}
}
