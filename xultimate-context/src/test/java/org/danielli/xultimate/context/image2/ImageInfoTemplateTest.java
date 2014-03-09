package org.danielli.xultimate.context.image2;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.danielli.xultimate.context.image2.ImageException;
import org.danielli.xultimate.context.image2.ImageInfoException;
import org.danielli.xultimate.context.image2.ImageInfoTemplate;
import org.danielli.xultimate.context.image2.awt.ImageUtils;
import org.danielli.xultimate.context.image2.config.DefaultImageResource;
import org.danielli.xultimate.context.image2.config.ImageFormat;
import org.danielli.xultimate.context.image2.config.WrapperImageResource;
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
@ContextConfiguration(locations = { "classpath:/image2/applicationContext-service-image.xml" })
public class ImageInfoTemplateTest {

	@Resource
	private ImageInfoTemplate awtImageInfoTemplate;
	
	@Resource
	private ImageInfoTemplate im4javaImageInfoTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageInfoTemplateTest.class);
	
//	@Test
	public void testGetImageInfo() throws ImageInfoException, IOException {
		DefaultImageResource srcImageResource = new DefaultImageResource(new ClassPathResource("image2/test.jpg").getFile());
		PerformanceMonitor.start("ImageInfoTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 50; j++) {
				srcImageResource.getImageInfo();
			}
			PerformanceMonitor.mark("im4javaImageInfoTemplate" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
		LOGGER.info(ToStringBuilderUtils.reflectionToString(new DefaultImageResource(new ClassPathResource("image2/test.jpg").getFile()).getImageInfo()));
	
		WrapperImageResource destImageResource = new WrapperImageResource();
		im4javaImageInfoTemplate.convertImage(srcImageResource, destImageResource);
		ImageUtils.writeBufferedImage(destImageResource.getBufferedImage(), ImageFormat.JPEG, new File("/home/toc/Desktop/test3.jpg"), 50);
	}

	@Test
	public void testConvertImage() throws ImageException, IOException {
		DefaultImageResource srcImageResource = new DefaultImageResource(new ClassPathResource("image2/test.jpg").getFile());
		PerformanceMonitor.start("ImageInfoTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				awtImageInfoTemplate.convertImage(srcImageResource, new WrapperImageResource(new File("/home/toc/Desktop/test1.jpg")));
			}
			PerformanceMonitor.mark("awtImageInfoTemplate" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				awtImageInfoTemplate.convertImage(srcImageResource, new WrapperImageResource());
			}
			PerformanceMonitor.mark("awtImageInfoTemplateBufferedImage" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				im4javaImageInfoTemplate.convertImage(srcImageResource, new WrapperImageResource(new File("/home/toc/Desktop/test2.jpg")));
			}
			PerformanceMonitor.mark("im4javaImageInfoTemplate" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				im4javaImageInfoTemplate.convertImage(srcImageResource, new WrapperImageResource());
			}
			PerformanceMonitor.mark("im4javaImageInfoTemplateBufferedImage" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}

}
