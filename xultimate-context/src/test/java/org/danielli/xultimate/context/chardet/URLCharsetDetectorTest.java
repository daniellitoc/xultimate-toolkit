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
@ContextConfiguration(locations = { "classpath*:/applicationContext-service-chardet.xml" })
public class URLCharsetDetectorTest {

	@Resource
	private URLCharsetDetector icpu4jUrlCharsetDetector;
	
	@Resource
	private URLCharsetDetector jchardetUrlCharsetDetector;
	
	@Resource
	private URLCharsetDetector cpdetectorUrlCharsetDetector;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(URLCharsetDetectorTest.class);
	
	@Test
	public void testDetect() {
		for (int i = 0; i < 20; i++) {
			try {
				for (Charset charset : icpu4jUrlCharsetDetector.detect(new URL("http://www-archive.mozilla.org/projects/intl/chardet.html"))) {
					LOGGER.info(charset.displayName());
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
			try {
				for (Charset charset : jchardetUrlCharsetDetector.detect(new URL("http://www-archive.mozilla.org/projects/intl/chardet.html"))) {
					LOGGER.info(charset.displayName());
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
			try {
				for (Charset charset : cpdetectorUrlCharsetDetector.detect(new URL("http://www-archive.mozilla.org/projects/intl/chardet.html"))) {
					LOGGER.info(charset.displayName());
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		PerformanceMonitor.start("URLCharsetDetectorTest");
		for (int i = 0; i < 10; i++) {
			try {
				icpu4jUrlCharsetDetector.detect(new URL("http://www-archive.mozilla.org/projects/intl/chardet.html"));
			} catch (Exception e) {

			}
		}
		PerformanceMonitor.mark("icpu4jUrlCharsetDetector.detect()");
		for (int i = 0; i < 10; i++) {
			try {
				jchardetUrlCharsetDetector.detect(new URL("http://www-archive.mozilla.org/projects/intl/chardet.html"));
			} catch (Exception e) {
				
			}
		}
		PerformanceMonitor.mark("jchardetUrlCharsetDetector.detect()");
		for (int i = 0; i < 10; i++) {
			try {
				cpdetectorUrlCharsetDetector.detect(new URL("http://www-archive.mozilla.org/projects/intl/chardet.html"));
			} catch (Exception e) {
				
			}
		}
		PerformanceMonitor.mark("cpdetectorUrlCharsetDetector.detect()");

		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(false));
		PerformanceMonitor.remove();
	}
}
