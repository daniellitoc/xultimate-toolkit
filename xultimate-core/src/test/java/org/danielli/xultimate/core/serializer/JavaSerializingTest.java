package org.danielli.xultimate.core.serializer;

import javax.annotation.Resource;

import org.danielli.xultimate.core.serializer.java.ObjectSerializer;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-service-serializer.xml" })
public class JavaSerializingTest {

	@Resource(name = "objectSerializer")
	private ObjectSerializer objectSerializer;
	
//	@Test
	public void testBase() {
		User person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		
		byte[] data = objectSerializer.serialize(person);
		person = (User) objectSerializer.deserialize(data, User.class);
		
		Assert.assertEquals("Daniel Li", person.getName());
		Assert.assertEquals((Integer) 19, person.getAge());
	}
	
	@Test
	public void test() {
		User person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		
		PerformanceMonitor.start("SerializerTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				byte[] data = objectSerializer.serialize(person);
				person = (User) objectSerializer.deserialize(data, User.class);
			}
			PerformanceMonitor.mark("JavaSerializing" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
}
