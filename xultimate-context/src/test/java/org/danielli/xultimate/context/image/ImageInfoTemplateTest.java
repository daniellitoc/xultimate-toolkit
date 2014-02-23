package org.danielli.xultimate.context.image;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.danielli.xultimate.util.builder.ToStringBuilderUtils;
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
@ContextConfiguration(locations = { "classpath:/image/applicationContext-service-image.xml" })
public class ImageInfoTemplateTest {

	@Resource
	private ImageInfoTemplate awtImageInfoTemplate;
	
	@Resource
	private ImageInfoTemplate im4javaImageInfoTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageInfoTemplateTest.class);
	
//	@Test
	public void testGetImageInfo() throws ImageInfoException, IOException {
		PerformanceMonitor.start("ImageInfoTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 50; j++) {
				awtImageInfoTemplate.getImageInfo(new ClassPathResource("image/test.jpg").getFile());
			}
			PerformanceMonitor.mark("awtImageInfoTemplate" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 50; j++) {
				im4javaImageInfoTemplate.getImageInfo(new ClassPathResource("image/test.jpg").getFile());
			}
			PerformanceMonitor.mark("im4javaImageInfoTemplate" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
		LOGGER.info(ToStringBuilderUtils.reflectionToString(awtImageInfoTemplate.getImageInfo(new ClassPathResource("image/test.jpg").getFile())));
		LOGGER.info(ToStringBuilderUtils.reflectionToString(im4javaImageInfoTemplate.getImageInfo(new ClassPathResource("image/test.jpg").getFile())));
	}

	@Test
	public void testConvertImage() throws ImageException, IOException {
		PerformanceMonitor.start("ImageInfoTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				awtImageInfoTemplate.convertImage(new ClassPathResource("image/test.jpg").getFile(), new File("/home/toc/Desktop/test1.jpg"));
			}
			PerformanceMonitor.mark("awtImageInfoTemplate" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				im4javaImageInfoTemplate.convertImage(new ClassPathResource("image/test.jpg").getFile(), new File("/home/toc/Desktop/test2.jpg"));
			}
			PerformanceMonitor.mark("im4javaImageInfoTemplate" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}

}
