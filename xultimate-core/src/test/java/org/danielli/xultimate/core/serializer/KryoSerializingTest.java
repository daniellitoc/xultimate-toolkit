package org.danielli.xultimate.core.serializer;

import javax.annotation.Resource;

import org.danielli.xultimate.core.serializer.kryo.MainKryoSerializer;
import org.danielli.xultimate.core.serializer.kryo.RpcKryoSerializer;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-service-serializer.xml" })
public class KryoSerializingTest {
	
	@Resource(name = "mainKryoSerializer")
	private MainKryoSerializer mainKryoSerializer;
	
	@Resource(name = "rpcKryoSerializer")
	private RpcKryoSerializer rpcKryoSerializer;
	
//	@Test
	public void testBase() {
		User person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		
		byte[] data = mainKryoSerializer.serialize(person);
		person = (User) mainKryoSerializer.deserialize(data, User.class);
		
		Assert.assertEquals("Daniel Li", person.getName());
		Assert.assertEquals((Integer) 19, person.getAge());
		
		person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		
		data = rpcKryoSerializer.serialize(person);
		person = (User) rpcKryoSerializer.deserialize(data, Object.class);
		
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
				byte[] data = mainKryoSerializer.serialize(person);
				person = (User) mainKryoSerializer.deserialize(data, User.class);
			}
			PerformanceMonitor.mark("mainKryoSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				byte[] data = rpcKryoSerializer.serialize(person);
				person = (User) rpcKryoSerializer.deserialize(data, Object.class);
			}
			PerformanceMonitor.mark("rpcKryoSerializer" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
}
