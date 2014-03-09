package org.danielli.xultimate.context.image2;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.danielli.xultimate.context.image2.ImageException;
import org.danielli.xultimate.context.image2.ImageResizeTemplate;
import org.danielli.xultimate.context.image2.config.DefaultImageResource;
import org.danielli.xultimate.context.image2.config.GeometryOperator;
import org.danielli.xultimate.context.image2.config.Gravity;
import org.danielli.xultimate.context.image2.config.ImageGeometry;
import org.danielli.xultimate.context.image2.config.ImageSize;
import org.danielli.xultimate.context.image2.config.WrapperImageResource;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/image2/applicationContext-service-image.xml" })
public class ImageResizeTemplateTest {
	
	@Resource
	private ImageResizeTemplate awtImageResizeTemplate;
	
	@Resource
	private ImageResizeTemplate im4javaImageResizeTemplate;
	
//	@Test
	public void test1() throws ImageException, IOException {
		DefaultImageResource srcImageResource = new DefaultImageResource(new ClassPathResource("image2/test.jpg").getFile());
		PerformanceMonitor.start("ImageResizeTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource(new File("/home/toc/Desktop/test1.jpg"));
				awtImageResizeTemplate.resizeImage(srcImageResource, destImageResource, new ImageGeometry(new ImageSize(640, 480), GeometryOperator.Emphasize));
			}
			PerformanceMonitor.mark("awtImageResizeTemplate" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource();
				awtImageResizeTemplate.resizeImage(srcImageResource, destImageResource, new ImageGeometry(new ImageSize(640, 480), GeometryOperator.Emphasize));
			}
			PerformanceMonitor.mark("awtImageResizeTemplateBufferedImage" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource(new File("/home/toc/Desktop/test2.jpg"));
				im4javaImageResizeTemplate.resizeImage(srcImageResource, destImageResource, new ImageGeometry(new ImageSize(640, 480), GeometryOperator.Emphasize));
			}
			PerformanceMonitor.mark("im4javaImageResizeTemplate" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource();
				im4javaImageResizeTemplate.resizeImage(srcImageResource, destImageResource, new ImageGeometry(new ImageSize(640, 480), GeometryOperator.Emphasize));
			}
			PerformanceMonitor.mark("im4javaImageResizeTemplateBufferedImage" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
	@Test
	public void test2() throws ImageException, IOException {
		DefaultImageResource srcImageResource = new DefaultImageResource(new ClassPathResource("image2/test.jpg").getFile());
		PerformanceMonitor.start("ImageInfoTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource(new File("/home/toc/Desktop/test1.jpg"));
				awtImageResizeTemplate.resizeImage(srcImageResource, destImageResource,  new ImageGeometry(new ImageSize(640, 480), GeometryOperator.Maximum));
			}
			PerformanceMonitor.mark("awtImageResizeTemplate" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource();
				awtImageResizeTemplate.resizeImage(srcImageResource, destImageResource,  new ImageGeometry(new ImageSize(640, 480), GeometryOperator.Maximum));
			}
			PerformanceMonitor.mark("awtImageResizeTemplateBufferedImage" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource(new File("/home/toc/Desktop/test2.jpg"));
				im4javaImageResizeTemplate.resizeImage(srcImageResource, destImageResource,  new ImageGeometry(new ImageSize(640, 480), GeometryOperator.Maximum));
			}
			PerformanceMonitor.mark("im4javaImageResizeTemplate" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource();
				im4javaImageResizeTemplate.resizeImage(srcImageResource, destImageResource,  new ImageGeometry(new ImageSize(640, 480), GeometryOperator.Maximum));
			}
			PerformanceMonitor.mark("im4javaImageResizeTemplateBufferedImage" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
//	@Test
	public void test3() throws ImageException, IOException {
		DefaultImageResource srcImageResource = new DefaultImageResource(new ClassPathResource("image2/test.jpg").getFile());
		PerformanceMonitor.start("ImageInfoTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource(new File("/home/toc/Desktop/test1.jpg"));
				awtImageResizeTemplate.resizeImageAsFixed(srcImageResource, destImageResource,  new ImageGeometry(new ImageSize(640, 480), GeometryOperator.Maximum), Gravity.Center);
			}
			PerformanceMonitor.mark("awtImageResizeTemplate" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource();
				awtImageResizeTemplate.resizeImageAsFixed(srcImageResource, destImageResource,  new ImageGeometry(new ImageSize(640, 480), GeometryOperator.Maximum), Gravity.Center);
			}
			PerformanceMonitor.mark("awtImageResizeTemplateBufferedImage" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource(new File("/home/toc/Desktop/test2.jpg"));
				im4javaImageResizeTemplate.resizeImageAsFixed(srcImageResource, destImageResource,  new ImageGeometry(new ImageSize(640, 480), GeometryOperator.Maximum), Gravity.Center);
			}
			PerformanceMonitor.mark("im4javaImageResizeTemplate" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource();
				im4javaImageResizeTemplate.resizeImageAsFixed(srcImageResource, destImageResource,  new ImageGeometry(new ImageSize(640, 480), GeometryOperator.Maximum), Gravity.Center);
			}
			PerformanceMonitor.mark("im4javaImageResizeTemplateBufferedImage" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
}
