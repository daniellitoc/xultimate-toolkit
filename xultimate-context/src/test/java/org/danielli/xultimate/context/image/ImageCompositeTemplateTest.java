package org.danielli.xultimate.context.image;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.danielli.xultimate.context.image.model.Gravity;
import org.danielli.xultimate.context.image.model.ImageCoordinate;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/image/applicationContext-service-image.xml" })
public class ImageCompositeTemplateTest {

	@Resource
	private ImageCompositeTemplate awtImageCompositeTemplate;
	
	@Resource
	private ImageCompositeTemplate im4javaImageCompositeTemplate;
	
//	@Test
	public void test1() throws ImageException, IOException {
		File srcImageFile = new ClassPathResource("image/test.jpg").getFile();
		File waterImageFile = new ClassPathResource("image/go-home.png").getFile();
		PerformanceMonitor.start("ImageCompositeTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				awtImageCompositeTemplate.addWatermarkImage(srcImageFile, new File("/home/toc/Desktop/test1.jpg"), waterImageFile, Gravity.Center);
			}
			PerformanceMonitor.mark("awtImageCompositeTemplate" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				im4javaImageCompositeTemplate.addWatermarkImage(srcImageFile, new File("/home/toc/Desktop/test2.jpg"), waterImageFile, Gravity.Center);
			}
			PerformanceMonitor.mark("im4javaImageCompositeTemplate" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
//	@Test
	public void test2() throws ImageException, IOException {
		File srcImageFile = new ClassPathResource("image/test.jpg").getFile();
		File waterImageFile = new ClassPathResource("image/go-home.png").getFile();
		PerformanceMonitor.start("ImageCompositeTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				awtImageCompositeTemplate.addWatermarkImage(srcImageFile, new File("/home/toc/Desktop/test1.jpg"), waterImageFile, new ImageCoordinate(30, 70));
			}
			PerformanceMonitor.mark("awtImageCompositeTemplate" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				im4javaImageCompositeTemplate.addWatermarkImage(srcImageFile, new File("/home/toc/Desktop/test2.jpg"), waterImageFile, new ImageCoordinate(30, 70));
			}
			PerformanceMonitor.mark("im4javaImageCompositeTemplate" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
	@Test
	public void test3() throws ImageException, IOException {
		File srcImageFile = new ClassPathResource("image/test.jpg").getFile();
		PerformanceMonitor.start("ImageCompositeTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				awtImageCompositeTemplate.addWatermarkText(srcImageFile, new File("/home/toc/Desktop/test1.jpg"), "Avril Lavigne",new Font("Ubuntu", Font.PLAIN, 50), Color.BLUE, Gravity.Center);
			}
			PerformanceMonitor.mark("awtImageCompositeTemplate" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				im4javaImageCompositeTemplate.addWatermarkText(srcImageFile, new File("/home/toc/Desktop/test2.jpg"), "Avril Lavigne", new Font("Ubuntu", Font.PLAIN, 50), Color.BLUE, Gravity.Center);
			}
			PerformanceMonitor.mark("im4javaImageCompositeTemplate" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
//	@Test
	public void test4() throws ImageException, IOException {
		File srcImageFile = new ClassPathResource("image/test.jpg").getFile();
		PerformanceMonitor.start("ImageCompositeTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				awtImageCompositeTemplate.addWatermarkText(srcImageFile, new File("/home/toc/Desktop/test1.jpg"), "Avril Lavigne",new Font("Ubuntu", Font.PLAIN, 50), Color.BLUE, new ImageCoordinate(0, 0));
			}
			PerformanceMonitor.mark("awtImageCompositeTemplate" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				im4javaImageCompositeTemplate.addWatermarkText(srcImageFile, new File("/home/toc/Desktop/test2.jpg"), "Avril Lavigne", new Font("Ubuntu", Font.PLAIN, 50), Color.BLUE, new ImageCoordinate(0, 0));
			}
			PerformanceMonitor.mark("im4javaImageCompositeTemplate" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	

}
