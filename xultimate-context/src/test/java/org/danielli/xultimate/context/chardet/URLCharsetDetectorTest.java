package org.danielli.xultimate.context.chardet;

import java.net.URL;
import java.nio.charset.Charset;

import javax.annotation.Resource;

import org.danielli.xultimate.context.chardet.support.URLCharsetDetector;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-service-chardet.xml" })
public class URLCharsetDetectorTest {

	@Resource
	private URLCharsetDetector icpu4jUrlCharsetDetector;
	
	@Resource
	private URLCharsetDetector jchardetUrlCharsetDetector;
	
	@Resource
	private URLCharsetDetector cpdetectorUrlCharsetDetector;
	
	@Resource
	private URLCharsetDetector juniversalchardetUrlCharsetDetector;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(URLCharsetDetectorTest.class);
	
	@Test
	public void test() {
		try {
			for (Charset charset : icpu4jUrlCharsetDetector.detect(new URL("http://www.taobao.com/"))) {
				LOGGER.info(charset.displayName());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		try {
			for (Charset charset : jchardetUrlCharsetDetector.detect(new URL("http://www.taobao.com/"))) {
				LOGGER.info(charset.displayName());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		try {
			for (Charset charset : cpdetectorUrlCharsetDetector.detect(new URL("http://www.taobao.com/"))) {
				LOGGER.info(charset.displayName());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		try {
			for (Charset charset : juniversalchardetUrlCharsetDetector.detect(new URL("http://www.taobao.com/"))) {
				LOGGER.info(charset.displayName());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
//	@Test
	public void testDetect() {
		PerformanceMonitor.start("URLCharsetDetectorTest");
		try {
			icpu4jUrlCharsetDetector.detect(new URL("http://www.taobao.com/"));
		} catch (Exception e) {

		}
		PerformanceMonitor.mark("icpu4jUrlCharsetDetector.detect()");
		try {
			jchardetUrlCharsetDetector.detect(new URL("http://www.taobao.com/"));
		} catch (Exception e) {
			
		}
		PerformanceMonitor.mark("jchardetUrlCharsetDetector.detect()");
//		try {
//			cpdetectorUrlCharsetDetector.detect(new URL("http://www.taobao.com/"));
//		} catch (Exception e) {
//			
//		}
//		PerformanceMonitor.mark("cpdetectorUrlCharsetDetector.detect()");
		try {
			juniversalchardetUrlCharsetDetector.detect(new URL("http://www.taobao.com/"));
		} catch (Exception e) {
			
		}
		PerformanceMonitor.mark("juniversalchardetUrlCharsetDetector.detect()");
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
}
