package org.danielli.xultimate.orm.mybatis.area;

import javax.annotation.Resource;

import org.danielli.xultimate.orm.mybatis.area.service.AreaService;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试不使用事物注解、使用事物注解(NOT_SUPPORT)、使用事物注解(REQUIRED,readOnly=false)、使用事物注解(REQUIRED,readOnly=true)的执行效率。
 * 
 * @author Daniel Li
 * @since 15 Jun 2013
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-service-config.xml", "classpath:applicationContext-service-crypto.xml", "classpath*:applicationContext-dao-*.xml", "classpath:applicationContext-service-generic.xml" })
public class PerformanceTest {

	@Resource(name = "areaServiceImpl")
	private AreaService areaService;
	
	@Test
	public void test() throws Exception {
		PerformanceMonitor.start("PerformanceTest");
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10000; j++) {
				areaService.findAllByTransaction();
			}
			PerformanceMonitor.mark("areaService.findAllByTransaction()");
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10000; j++) {
				areaService.findAllByReadOnly();
			}
			PerformanceMonitor.mark("areaService.findAllByReadOnly()");
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10000; j++) {
				areaService.findAllByNotSupport();
			}
			PerformanceMonitor.mark("areaService.findAllByNotSupport()");
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10000; j++) {
				areaService.findAll();
			}
			PerformanceMonitor.mark("areaService.findAll()");
		}
		
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
}
