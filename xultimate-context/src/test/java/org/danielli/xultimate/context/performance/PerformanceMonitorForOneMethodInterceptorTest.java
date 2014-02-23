package org.danielli.xultimate.context.performance;

import javax.annotation.Resource;

import org.danielli.xultimate.context.performance.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/performance/applicationContext-service-performance-one.xml" })
public class PerformanceMonitorForOneMethodInterceptorTest {

	@Resource(name = "testService")
	private TestService testService;
	
	@Test
	public void test() {
		testService.doSomething();
	}
	
}
