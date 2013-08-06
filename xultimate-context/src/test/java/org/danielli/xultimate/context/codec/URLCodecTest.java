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
@ContextConfiguration(locations = { "classpath:/applicationContext-service-codec.xml" })
public class URLCodecTest {
	
	@Resource(name = "stringStringURLCodec")
	private Encoder<String, String> encoder;
	@Resource(name = "stringStringURLCodec")
	private Decoder<String, String> decoder;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(URLCodecTest.class);
	
	@Test
	public void test() {
		String source = "name=danielli";
		PerformanceMonitor.start("test");
		LOGGER.info("{}: {}", new Object[] { source, encoder.encode(source) });
		PerformanceMonitor.mark("URLCodec.encode()");
		source = "name%3Ddanielli";
		LOGGER.info("{}: {}", new Object[] { source, decoder.decode(source) });
		PerformanceMonitor.mark("URLCodec.decode()");
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(false));
		PerformanceMonitor.remove();

	}
}
