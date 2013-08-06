package org.danielli.xultimate.core.serializer;

import javax.annotation.Resource;

import org.danielli.xultimate.core.serializer.java.ObjectSerializer;
import org.danielli.xultimate.core.serializer.kryo.RpcKryoSerializer;
import org.danielli.xultimate.core.serializer.protostuff.RpcProtobufSerializer;
import org.danielli.xultimate.core.serializer.protostuff.RpcProtostuffSerializer;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-service-serializer.xml" })
public class SerializerTest {
	
	@Resource
	private ObjectSerializer objectSerializer;
	
	@Resource
	private RpcKryoSerializer rpcKryoSerializer;
	
	@Resource
	private RpcProtostuffSerializer rpcProtostuffSerializer;

	@Resource
	private RpcProtobufSerializer rpcProtobufSerializer;
	
	@Test
	public void testObject() {
		PerformanceMonitor.start("SerializerTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				byte[] data = objectSerializer.serialize(person);
				person = (User) objectSerializer.deserialize(data, Object.class);
			}
			PerformanceMonitor.mark("objectSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				byte[] data = rpcKryoSerializer.serialize(person);
				person = (User) rpcKryoSerializer.deserialize(data, Object.class);
			}
			PerformanceMonitor.mark("rpcKryoSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				byte[] data = rpcProtostuffSerializer.serialize(person);
				person = (User) rpcProtostuffSerializer.deserialize(data, Object.class);
			}
			PerformanceMonitor.mark("rpcProtostuffSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				byte[] data = rpcProtobufSerializer.serialize(person);
				person = (User) rpcProtobufSerializer.deserialize(data, Object.class);
			}
			PerformanceMonitor.mark("rpcProtobufSerializer" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}

}
