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
		PerformanceMonitor.start("ImageCompositeTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				awtImageCompositeTemplate.addWatermarkImage(new ClassPathResource("image/test.jpg").getFile(), new File("/home/toc/Desktop/test1.gif"), new ClassPathResource("image/go-home.png").getFile(), Gravity.Center);
			}
			PerformanceMonitor.mark("awtImageCompositeTemplate" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				im4javaImageCompositeTemplate.addWatermarkImage(new ClassPathResource("image/test.jpg").getFile(), new File("/home/toc/Desktop/test2.gif"), new ClassPathResource("image/go-home.png").getFile(), Gravity.Center);
			}
			PerformanceMonitor.mark("im4javaImageCompositeTemplate" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
//	@Test
	public void test2() throws ImageException, IOException {
		PerformanceMonitor.start("ImageCompositeTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				awtImageCompositeTemplate.addWatermarkImage(new ClassPathResource("image/test.jpg").getFile(), new File("/home/toc/Desktop/test1.gif"), new ClassPathResource("image/go-home.png").getFile(), new ImageCoordinate(30, 70));
			}
			PerformanceMonitor.mark("awtImageCompositeTemplate" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				im4javaImageCompositeTemplate.addWatermarkImage(new ClassPathResource("image/test.jpg").getFile(), new File("/home/toc/Desktop/test2.gif"), new ClassPathResource("image/go-home.png").getFile(), new ImageCoordinate(30, 70));
			}
			PerformanceMonitor.mark("im4javaImageCompositeTemplate" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
//	@Test
	public void test3() throws ImageException, IOException {
		PerformanceMonitor.start("ImageCompositeTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				awtImageCompositeTemplate.addWatermarkText(new ClassPathResource("image/test.jpg").getFile(), new File("/home/toc/Desktop/test1.gif"), "Avril Lavigne",new Font("Ubuntu", Font.PLAIN, 50), Color.BLUE, Gravity.Center);
			}
			PerformanceMonitor.mark("awtImageCompositeTemplate" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				im4javaImageCompositeTemplate.addWatermarkText(new ClassPathResource("image/test.jpg").getFile(), new File("/home/toc/Desktop/test2.gif"), "Avril Lavigne", new Font("Ubuntu", Font.PLAIN, 50), Color.BLUE, Gravity.Center);
			}
			PerformanceMonitor.mark("im4javaImageCompositeTemplate" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
	@Test
	public void test4() throws ImageException, IOException {
		PerformanceMonitor.start("ImageCompositeTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				awtImageCompositeTemplate.addWatermarkText(new ClassPathResource("image/test.jpg").getFile(), new File("/home/toc/Desktop/test1.gif"), "Avril Lavigne",new Font("Ubuntu", Font.PLAIN, 50), Color.BLUE, new ImageCoordinate(0, 0));
			}
			PerformanceMonitor.mark("awtImageCompositeTemplate" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				im4javaImageCompositeTemplate.addWatermarkText(new ClassPathResource("image/test.jpg").getFile(), new File("/home/toc/Desktop/test2.gif"), "Avril Lavigne", new Font("Ubuntu", Font.PLAIN, 50), Color.BLUE, new ImageCoordinate(0, 0));
			}
			PerformanceMonitor.mark("im4javaImageCompositeTemplate" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	

}
