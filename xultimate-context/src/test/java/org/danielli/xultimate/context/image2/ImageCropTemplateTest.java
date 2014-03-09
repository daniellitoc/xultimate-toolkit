package org.danielli.xultimate.context.image2;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.danielli.xultimate.context.image2.ImageCropTemplate;
import org.danielli.xultimate.context.image2.ImageException;
import org.danielli.xultimate.context.image2.config.DefaultImageResource;
import org.danielli.xultimate.context.image2.config.Gravity;
import org.danielli.xultimate.context.image2.config.ImageCoordinate;
import org.danielli.xultimate.context.image2.config.ImageGeometryCoordinate;
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
public class ImageCropTemplateTest {

	@Resource
	private ImageCropTemplate awtImageCropTemplateImpl;
	
	@Resource
	private ImageCropTemplate im4javaImageCropTemplateImpl;
	
//	@Test
	public void test1() throws ImageException, IOException {
		DefaultImageResource srcImageResource = new DefaultImageResource(new ClassPathResource("image2/test.jpg").getFile());
		PerformanceMonitor.start("ImageCropTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource(new File("/home/toc/Desktop/test1.jpg"));
				awtImageCropTemplateImpl.cropImage(srcImageResource, destImageResource, new ImageSize(640, 480), Gravity.Center);
			}
			PerformanceMonitor.mark("awtImageCropTemplateImpl" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource();
				awtImageCropTemplateImpl.cropImage(srcImageResource, destImageResource, new ImageSize(640, 480), Gravity.Center);
			}
			PerformanceMonitor.mark("awtImageCropTemplateImplBufferedImage" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource(new File("/home/toc/Desktop/test2.jpg"));
				im4javaImageCropTemplateImpl.cropImage(srcImageResource, destImageResource, new ImageSize(640, 480), Gravity.Center);
			}
			PerformanceMonitor.mark("im4javaImageCropTemplateImpl" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource();
				im4javaImageCropTemplateImpl.cropImage(srcImageResource, destImageResource, new ImageSize(640, 480), Gravity.Center);
			}
			PerformanceMonitor.mark("im4javaImageCropTemplateImplBufferedImage" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
	@Test
	public void test2() throws ImageException, IOException {
		DefaultImageResource srcImageResource = new DefaultImageResource(new ClassPathResource("image2/test.jpg").getFile());
		PerformanceMonitor.start("ImageCropTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource(new File("/home/toc/Desktop/test1.jpg"));
				awtImageCropTemplateImpl.cropImage(srcImageResource, destImageResource, new ImageGeometryCoordinate(new ImageSize(640, 480), new ImageCoordinate(100, 30)));
			}
			PerformanceMonitor.mark("awtImageCropTemplateImpl" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource();
				awtImageCropTemplateImpl.cropImage(srcImageResource, destImageResource, new ImageGeometryCoordinate(new ImageSize(640, 480), new ImageCoordinate(100, 30)));
			}
			PerformanceMonitor.mark("awtImageCropTemplateImplBufferedImage" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource(new File("/home/toc/Desktop/test2.jpg"));
				im4javaImageCropTemplateImpl.cropImage(srcImageResource, destImageResource, new ImageGeometryCoordinate(new ImageSize(640, 480), new ImageCoordinate(100, 30)));
			}
			PerformanceMonitor.mark("im4javaImageCropTemplateImpl" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource();
				im4javaImageCropTemplateImpl.cropImage(srcImageResource, destImageResource, new ImageGeometryCoordinate(new ImageSize(640, 480), new ImageCoordinate(100, 30)));
			}
			PerformanceMonitor.mark("im4javaImageCropTemplateImplBufferedImage" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
}
