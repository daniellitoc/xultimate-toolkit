package org.danielli.xultimate.context.image2;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.danielli.xultimate.context.image2.ImageCompositeTemplate;
import org.danielli.xultimate.context.image2.ImageException;
import org.danielli.xultimate.context.image2.config.DefaultImageResource;
import org.danielli.xultimate.context.image2.config.Gravity;
import org.danielli.xultimate.context.image2.config.ImageCoordinate;
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
public class ImageCompositeTemplateTest {

	@Resource
	private ImageCompositeTemplate awtImageCompositeTemplate;
	
	@Resource
	private ImageCompositeTemplate im4javaImageCompositeTemplate;
	
//	@Test
	public void test1() throws ImageException, IOException {
		DefaultImageResource srcImageResource = new DefaultImageResource(new ClassPathResource("image2/test.jpg").getFile());
		DefaultImageResource waterImageResource = new DefaultImageResource(new ClassPathResource("image2/go-home.png").getFile());
		PerformanceMonitor.start("ImageCompositeTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource(new File("/home/toc/Desktop/test1.jpg"));
				awtImageCompositeTemplate.addWatermarkImage(srcImageResource, destImageResource, waterImageResource, Gravity.Center);
			}
			PerformanceMonitor.mark("awtImageCompositeTemplate" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource();
				awtImageCompositeTemplate.addWatermarkImage(srcImageResource, destImageResource, waterImageResource, Gravity.Center);
			}
			PerformanceMonitor.mark("awtImageCompositeTemplateBufferedImage" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource(new File("/home/toc/Desktop/test2.jpg"));
				im4javaImageCompositeTemplate.addWatermarkImage(srcImageResource, destImageResource, waterImageResource, Gravity.Center);
			}
			PerformanceMonitor.mark("im4javaImageCompositeTemplate" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource();
				im4javaImageCompositeTemplate.addWatermarkImage(srcImageResource, destImageResource, waterImageResource, Gravity.Center);
			}
			PerformanceMonitor.mark("im4javaImageCompositeTemplateBufferedImage" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
//	@Test
	public void test2() throws ImageException, IOException {
		DefaultImageResource srcImageResource = new DefaultImageResource(new ClassPathResource("image2/test.jpg").getFile());
		DefaultImageResource waterImageResource = new DefaultImageResource(new ClassPathResource("image2/go-home.png").getFile());
		PerformanceMonitor.start("ImageCompositeTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource(new File("/home/toc/Desktop/test1.jpg"));
				awtImageCompositeTemplate.addWatermarkImage(srcImageResource, destImageResource, waterImageResource, new ImageCoordinate(30, 70));
			}
			PerformanceMonitor.mark("awtImageCompositeTemplate" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource();
				awtImageCompositeTemplate.addWatermarkImage(srcImageResource, destImageResource, waterImageResource, new ImageCoordinate(30, 70));
			}
			PerformanceMonitor.mark("awtImageCompositeTemplateBufferedImage" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource(new File("/home/toc/Desktop/test2.jpg"));
				im4javaImageCompositeTemplate.addWatermarkImage(srcImageResource, destImageResource, waterImageResource, new ImageCoordinate(30, 70));
			}
			PerformanceMonitor.mark("im4javaImageCompositeTemplate" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource();
				im4javaImageCompositeTemplate.addWatermarkImage(srcImageResource, destImageResource, waterImageResource, new ImageCoordinate(30, 70));
			}
			PerformanceMonitor.mark("im4javaImageCompositeTemplateBufferedImage" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
//	@Test
	public void test3() throws ImageException, IOException {
		DefaultImageResource srcImageResource = new DefaultImageResource(new ClassPathResource("image2/test.jpg").getFile());
		PerformanceMonitor.start("ImageCompositeTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource(new File("/home/toc/Desktop/test1.jpg"));
				awtImageCompositeTemplate.addWatermarkText(srcImageResource, destImageResource, "Avril Lavigne",new Font("Ubuntu", Font.PLAIN, 50), Color.BLUE, Gravity.Center);
			}
			PerformanceMonitor.mark("awtImageCompositeTemplate" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource();
				awtImageCompositeTemplate.addWatermarkText(srcImageResource, destImageResource, "Avril Lavigne",new Font("Ubuntu", Font.PLAIN, 50), Color.BLUE, Gravity.Center);
			}
			PerformanceMonitor.mark("awtImageCompositeTemplateBufferedImage" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource(new File("/home/toc/Desktop/test2.jpg"));
				im4javaImageCompositeTemplate.addWatermarkText(srcImageResource, destImageResource, "Avril Lavigne", new Font("Ubuntu", Font.PLAIN, 50), Color.BLUE, Gravity.Center);
			}
			PerformanceMonitor.mark("im4javaImageCompositeTemplate" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource();
				im4javaImageCompositeTemplate.addWatermarkText(srcImageResource, destImageResource, "Avril Lavigne", new Font("Ubuntu", Font.PLAIN, 50), Color.BLUE, Gravity.Center);
			}
			PerformanceMonitor.mark("im4javaImageCompositeTemplateBufferedImage" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
	@Test
	public void test4() throws ImageException, IOException {
		DefaultImageResource srcImageResource = new DefaultImageResource(new ClassPathResource("image2/test.jpg").getFile());
		PerformanceMonitor.start("ImageCompositeTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource(new File("/home/toc/Desktop/test1.jpg"));
				awtImageCompositeTemplate.addWatermarkText(srcImageResource, destImageResource, "Avril Lavigne",new Font("Ubuntu", Font.PLAIN, 50), Color.BLUE, new ImageCoordinate(0, 0));
			}
			PerformanceMonitor.mark("awtImageCompositeTemplate" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource();
				awtImageCompositeTemplate.addWatermarkText(srcImageResource, destImageResource, "Avril Lavigne",new Font("Ubuntu", Font.PLAIN, 50), Color.BLUE, new ImageCoordinate(0, 0));
			}
			PerformanceMonitor.mark("awtImageCompositeTemplateBufferedImage" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource(new File("/home/toc/Desktop/test2.jpg"));
				im4javaImageCompositeTemplate.addWatermarkText(srcImageResource, destImageResource, "Avril Lavigne", new Font("Ubuntu", Font.PLAIN, 50), Color.BLUE, new ImageCoordinate(0, 0));
			}
			PerformanceMonitor.mark("im4javaImageCompositeTemplate" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10; j++) {
				WrapperImageResource destImageResource = new WrapperImageResource();
				im4javaImageCompositeTemplate.addWatermarkText(srcImageResource, destImageResource, "Avril Lavigne", new Font("Ubuntu", Font.PLAIN, 50), Color.BLUE, new ImageCoordinate(0, 0));
			}
			PerformanceMonitor.mark("im4javaImageCompositeTemplateBufferedImage" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	

}
