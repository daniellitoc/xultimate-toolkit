package org.danielli.xultimate.context.event;

import org.danielli.xultimate.context.util.BeanFactoryContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-service-event.xml", "classpath:/applicationContext-service-util.xml" })
public class EventTest {

	@Test
	public void testEvent() {
		LogEvent logEvent = new LogEvent(BeanFactoryContext.currentApplicationContext(), Thread.currentThread().toString());
		// 为单线程，事件监听器和发布器是同步的调用关系。
		// 可通过taskExecutor配置实现异步，SimpleApplicationEventMulticaster
		BeanFactoryContext.currentApplicationContext().publishEvent(logEvent);
	}
}
