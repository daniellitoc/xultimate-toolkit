package org.danielli.xultimate.core.serializer;

import javax.annotation.Resource;

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
	
	@Resource(name = "mainObjectSerializerProxy")
	private Serializer mainObjectSerializerProxy;
	
	@Resource(name = "mainObjectDeserializerProxy")
	private Deserializer mainObjectDeserializerProxy;
	
	@Resource(name = "rpcObjectSerializerProxy")
	private Serializer rpcObjectSerializerProxy;
	
	@Resource(name = "rpcObjectDeserializerProxy")
	private Deserializer rpcObjectDeserializerProxy;
	
//	@Test
	public void testBase() {
		User person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		
		byte[] data = mainObjectSerializerProxy.serialize(person);
		person = (User) mainObjectDeserializerProxy.deserialize(data, User.class);
		
		Assert.assertEquals("Daniel Li", person.getName());
		Assert.assertEquals((Integer) 19, person.getAge());
		
		person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		
		data = rpcObjectSerializerProxy.serialize(person);
		person = (User) rpcObjectDeserializerProxy.deserialize(data, Object.class);
		
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
				byte[] data = mainObjectSerializerProxy.serialize(person);
				person = mainObjectDeserializerProxy.deserialize(data, User.class);
			}
			PerformanceMonitor.mark("mainKryoSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				byte[] data = rpcObjectSerializerProxy.serialize(person);
				person = (User) rpcObjectDeserializerProxy.deserialize(data, Object.class);
			}
			PerformanceMonitor.mark("rpcKryoSerializer" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
}
