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
@ContextConfiguration(locations = { "classpath:/applicationContext-service-chardet.xml" })
public class FileCharsetDetectorTest {
	
	@Resource
	private FileCharsetDetector icpu4jFileCharsetDetector;
	@Resource
	private FileCharsetDetector jchardetFileCharsetDetector;
	@Resource
	private FileCharsetDetector cpdetectorFileCharsetDetector;
	@Resource
	private FileCharsetDetector juniversalchardetFileCharsetDetector;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileCharsetDetectorTest.class);
	
	@Test
	public void test() {
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
		try {
			for (Charset charset : juniversalchardetFileCharsetDetector.detect(new ClassPathResource("test.txt").getFile())) {
				LOGGER.info(charset.displayName());
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
//	@Test
	public void testDetect() {
		PerformanceMonitor.start("FileCharsetDetectorTest");
//		for (int i = 0; i < 5; i++) {
//			for (int j = 0; j < 10000; j++) {
//				try {
//					cpdetectorFileCharsetDetector.detect(new ClassPathResource("test.txt").getFile());
//				} catch (Exception e) {
//
//				}
//			}
//			PerformanceMonitor.mark("cpdetectorFileCharsetDetector.detect()" + i);
//		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10000; j++) {
				try {
					jchardetFileCharsetDetector.detect(new ClassPathResource("test.txt").getFile());
				} catch (Exception e) {
					
				}
			}
			PerformanceMonitor.mark("jchardetFileCharsetDetector.detect()" + i);
		}

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10000; j++) {
				try {
					icpu4jFileCharsetDetector.detect(new ClassPathResource("test.txt").getFile());
				} catch (Exception e) {
					
				}
			}
			PerformanceMonitor.mark("icpu4jFileCharsetDetector.detect()" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10000; j++) {
				try {
					juniversalchardetFileCharsetDetector.detect(new ClassPathResource("test.txt").getFile());
				} catch (Exception e) {
					
				}
			}
			PerformanceMonitor.mark("juniversalchardetFileCharsetDetector.detect()" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
}
