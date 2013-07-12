package org.danielli.xultimate.context.codec;

import javax.annotation.Resource;

import org.danielli.xultimate.util.StringUtils;
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
public class HexCodecTest {
	
	@Resource(name = "byteArrayStringHexCodec")
	private Encoder<byte[], String> encoder;
	@Resource(name = "byteArrayStringHexCodec")
	private Decoder<String, byte[]> decoder;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(URLCodecTest.class);
	
	@Test
	public void test() {
		String source = "daniellitoc";
		PerformanceMonitor.start("test");
		String result = encoder.encode(StringUtils.getBytesUtf8(source));
		LOGGER.info("{}: {}", new Object[] { source, result });
		PerformanceMonitor.mark("HexCodec.encode()");
		source = "64616e69656c6c69746f63";
		LOGGER.info("{}: {}", new Object[] { source, StringUtils.newStringUtf8(decoder.decode(result)) });
		PerformanceMonitor.mark("HexCodec.decode()");
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(false));
		PerformanceMonitor.remove();

	}
}
