package org.danielli.xultimate.context.image;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.danielli.xultimate.context.image.model.GeometryOperator;
import org.danielli.xultimate.context.image.model.Gravity;
import org.danielli.xultimate.context.image.model.ImageGeometry;
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
public class ImageResizeTemplateTest {
	
	@Resource
	private ImageResizeTemplate awtImageResizeTemplate;
	
	@Resource
	private ImageResizeTemplate im4javaImageResizeTemplate;
	
//	@Test
	public void test1() throws ImageException, IOException {
		File srcImageFile = new ClassPathResource("image/test.jpg").getFile();
		PerformanceMonitor.start("ImageResizeTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				awtImageResizeTemplate.resizeImage(srcImageFile, new File("/home/toc/Desktop/test1.jpg"), new ImageGeometry(new ImageSize(640, 480), GeometryOperator.Emphasize));
			}
			PerformanceMonitor.mark("awtImageResizeTemplate" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				im4javaImageResizeTemplate.resizeImage(srcImageFile, new File("/home/toc/Desktop/test2.jpg"), new ImageGeometry(new ImageSize(640, 480), GeometryOperator.Emphasize));
			}
			PerformanceMonitor.mark("im4javaImageResizeTemplate" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
	@Test
	public void test2() throws ImageException, IOException {
		File srcImageFile = new ClassPathResource("image/test.jpg").getFile();
		PerformanceMonitor.start("ImageInfoTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				awtImageResizeTemplate.resizeImage(srcImageFile, new File("/home/toc/Desktop/test1.jpg"),  new ImageGeometry(new ImageSize(640, 480), GeometryOperator.Maximum));
			}
			PerformanceMonitor.mark("awtImageInfoTemplate" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				im4javaImageResizeTemplate.resizeImage(srcImageFile, new File("/home/toc/Desktop/test2.jpg"),  new ImageGeometry(new ImageSize(640, 480), GeometryOperator.Maximum));
			}
			PerformanceMonitor.mark("im4javaImageInfoTemplate" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
//	@Test
	public void test3() throws ImageException, IOException {
		File srcImageFile = new ClassPathResource("image/test.jpg").getFile();
		PerformanceMonitor.start("ImageInfoTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				awtImageResizeTemplate.resizeImageAsFixed(srcImageFile, new File("/home/toc/Desktop/test1.jpg"), new ImageGeometry(new ImageSize(600, 480), GeometryOperator.Minimum) , Gravity.Center);
			}
			PerformanceMonitor.mark("awtImageInfoTemplate" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				im4javaImageResizeTemplate.resizeImageAsFixed(srcImageFile, new File("/home/toc/Desktop/test2.jpg"), new ImageGeometry(new ImageSize(600, 480), GeometryOperator.Minimum), Gravity.Center);
			}
			PerformanceMonitor.mark("im4javaImageInfoTemplate" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
}
