package org.danielli.xultimate.context.image;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.danielli.xultimate.context.image.model.Gravity;
import org.danielli.xultimate.context.image.model.ImageCoordinate;
import org.danielli.xultimate.context.image.model.ImageSize;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/image/applicationContext-service-image.xml" })
public class ImageCropTemplateTest {

	@Resource
	private ImageCropTemplate awtImageCropTemplateImpl;
	
	@Resource
	private ImageCropTemplate im4javaImageCropTemplateImpl;
	
//	@Test
	public void test1() throws ImageException, IOException {
		PerformanceMonitor.start("ImageCropTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				awtImageCropTemplateImpl.cropImage(new ClassPathResource("image/test.jpg").getFile(), new File("/home/toc/Desktop/test1.gif"), new ImageSize(640, 480), Gravity.Center);
			}
			PerformanceMonitor.mark("awtImageCropTemplateImpl" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				im4javaImageCropTemplateImpl.cropImage(new ClassPathResource("image/test.jpg").getFile(), new File("/home/toc/Desktop/test2.gif"), new ImageSize(640, 480), Gravity.Center);
			}
			PerformanceMonitor.mark("im4javaImageCropTemplateImpl" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
	@Test
	public void test2() throws ImageException, IOException {
		PerformanceMonitor.start("ImageCropTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				awtImageCropTemplateImpl.cropImage(new ClassPathResource("image/test.jpg").getFile(), new File("/home/toc/Desktop/test1.gif"), new ImageSize(640, 480), new ImageCoordinate(100, 30));
			}
			PerformanceMonitor.mark("awtImageCropTemplateImpl" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				im4javaImageCropTemplateImpl.cropImage(new ClassPathResource("image/test.jpg").getFile(), new File("/home/toc/Desktop/test2.gif"), new ImageSize(640, 480), new ImageCoordinate(100, 30));
			}
			PerformanceMonitor.mark("im4javaImageCropTemplateImpl" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
}
