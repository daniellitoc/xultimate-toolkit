package org.danielli.xultimate.context.chardet;

import java.nio.charset.Charset;

import javax.annotation.Resource;

import org.danielli.xultimate.context.chardet.support.FileCharsetDetector;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext-service-chardet.xml" })
public class FileCharsetDetectorTest {
	
	@Resource
	private FileCharsetDetector icpu4jFileCharsetDetector;
	@Resource
	private FileCharsetDetector jchardetFileCharsetDetector;
	@Resource
	private FileCharsetDetector cpdetectorFileCharsetDetector;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileCharsetDetectorTest.class);
	
	@Test
	public void testDetect() {
		for (int i = 0; i < 50000; i++) {
			try {
				for (Charset charset : cpdetectorFileCharsetDetector.detect(new ClassPathResource("test.txt").getFile())) {
					LOGGER.info(charset.displayName());
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
			try {
				for (Charset charset : jchardetFileCharsetDetector.detect(new ClassPathResource("test.txt").getFile())) {
					LOGGER.info(charset.displayName());
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
			try {
				for (Charset charset : icpu4jFileCharsetDetector.detect(new ClassPathResource("test.txt").getFile())) {
					LOGGER.info(charset.displayName());
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		PerformanceMonitor.start("FileCharsetDetectorTest");
		for (int i = 0; i < 10000; i++) {
			try {
				cpdetectorFileCharsetDetector.detect(new ClassPathResource("test.txt").getFile());
			} catch (Exception e) {

			}
		}
		PerformanceMonitor.mark("cpdetectorFileCharsetDetector.detect()");
		for (int i = 0; i < 10000; i++) {
			try {
				jchardetFileCharsetDetector.detect(new ClassPathResource("test.txt").getFile());
			} catch (Exception e) {
				
			}
		}
		PerformanceMonitor.mark("jchardetFileCharsetDetector.detect()");
		for (int i = 0; i < 10000; i++) {
			try {
				icpu4jFileCharsetDetector.detect(new ClassPathResource("test.txt").getFile());
			} catch (Exception e) {
				
			}
		}
		PerformanceMonitor.mark("icpu4jFileCharsetDetector.detect()");

		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(false));
		PerformanceMonitor.remove();
	}
}
