package org.danielli.xultimate.context.format;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.danielli.xultimate.context.format.support.FreeMarkerTemplateFormatter;
import org.danielli.xultimate.context.format.support.MessageFormatter;
import org.danielli.xultimate.context.format.support.SpelFormatter;
import org.danielli.xultimate.context.format.support.StringTemplateV3Formatter;
import org.danielli.xultimate.context.format.support.StringTemplateV4Formatter;
import org.danielli.xultimate.context.format.support.VelocityEngineFormatter;
import org.danielli.xultimate.util.ArrayUtils;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/formatter/applicationContext-service-formatter.xml" })
public class FormatterTest {

	@Resource(name = "freeMarkerFormatter")
	private FreeMarkerTemplateFormatter freeMarkerTemplateFormatter;
	
	@Resource(name = "messageFormatter")
	private MessageFormatter messageFormatter;
	
	@Resource(name = "spelFormatter")
	private SpelFormatter spelFormatter;
	
	@Resource(name = "stringTemplateV3Formatter")
	private StringTemplateV3Formatter stringTemplateV3Formatter;
	
	@Resource(name = "stringTemplateV4Formatter")
	private StringTemplateV4Formatter stringTemplateV4Formatter;
	
	@Resource(name = "velocityEngineFormatter")
	private VelocityEngineFormatter velocityEngineFormatter;
	
	private Map<String, Object> data = new HashMap<String, Object>();
	{
		data.put("userName", "Daniel Li");
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FormatterTest.class);
	
//	@Test
	public void test() {
		LOGGER.info(freeMarkerTemplateFormatter.format("Hello World, ${userName}", data));
		LOGGER.info(messageFormatter.format("Hello World, {0}", ArrayUtils.toArray("Daniel Li")));
		LOGGER.info(spelFormatter.format("Hello World, ${#userName}", data));
		LOGGER.info(stringTemplateV3Formatter.format("Hello World, $userName$", data));
		LOGGER.info(stringTemplateV4Formatter.format("Hello World, <userName>", data));
		LOGGER.info(velocityEngineFormatter.format("Hello World, ${userName}", data));
	}
	
	@Test
	public void testFormat() {
		PerformanceMonitor.start("FormatterTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10000; j++) {
				freeMarkerTemplateFormatter.format("Hello World, ${userName}", data);
			}
			PerformanceMonitor.mark("freeMarkerTemplateFormatter" + i);
		}

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10000; j++) {
				messageFormatter.format("Hello World, {0}", new Object[] { "Daniel Li" });
			}
			PerformanceMonitor.mark("messageFormatter" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10000; j++) {
				spelFormatter.format("Hello World, ${#userName}", data);
			}
			PerformanceMonitor.mark("spelFormatter" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10000; j++) {
				stringTemplateV3Formatter.format("Hello World, $userName$", data);
			}
			PerformanceMonitor.mark("stringTemplateV3Formatter" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10000; j++) {
				stringTemplateV4Formatter.format("Hello World, <userName>", data);
			}
			PerformanceMonitor.mark("stringTemplateV4Formatter" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10000; j++) {
				velocityEngineFormatter.format("Hello World, ${userName}", data);
			}
			PerformanceMonitor.mark("velocityEngineFormatter" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
}
