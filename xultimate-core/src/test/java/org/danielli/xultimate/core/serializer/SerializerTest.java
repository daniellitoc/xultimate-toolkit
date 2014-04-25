package org.danielli.xultimate.core.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.annotation.Resource;

import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.esotericsoftware.kryo.io.Input;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-service-serializer.xml" })
public class SerializerTest {
	
	@Resource(name = "rpcObjectSerializerProxy")
	private Serializer rpcObjectSerializerProxy;
	
	@Resource(name = "rpcObjectDeserializerProxy")
	private Deserializer rpcObjectDeserializerProxy;
	
	@Resource(name = "rpcKryoSerializerProxy")
	private Serializer rpcKryoSerializer;
	
	@Resource(name = "rpcKryoDeserializerProxy")
	private Deserializer rpcKryoDeserializer;
	
	@Resource(name = "rpcProtobufSerializerProxy")
	private Serializer rpcProtobufSerializer;
	
	@Resource(name = "rpcProtobufDeserializerProxy")
	private Deserializer rpcProtobufDeserializer;

	@Resource(name = "rpcProtostuffSerializerProxy")
	private Serializer rpcProtostuffSerializer;
	
	@Resource(name = "rpcProtostuffDeserializerProxy")
	private Deserializer rpcProtostuffDeserializer;
	
	@Test
	public void testObject() {
		PerformanceMonitor.start("SerializerTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				byte[] data = rpcObjectSerializerProxy.serialize(person);
				person = (User) rpcObjectDeserializerProxy.deserialize(data, Object.class);
			}
			PerformanceMonitor.mark("objectSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				rpcObjectSerializerProxy.serialize(person, outputStream);
				ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
				person = (User) rpcObjectDeserializerProxy.deserialize(inputStream, Object.class);
			}
			PerformanceMonitor.mark("objectSerializer IO" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				byte[] data = rpcKryoSerializer.serialize(person);
				person = (User) rpcKryoDeserializer.deserialize(data, Object.class);
			}
			PerformanceMonitor.mark("rpcKryoSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				rpcKryoSerializer.serialize(person, outputStream);
				ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
				person = (User) rpcKryoDeserializer.deserialize(new Input(inputStream), Object.class);
			}
			PerformanceMonitor.mark("rpcKryoSerializer IO" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				byte[] data = rpcProtostuffSerializer.serialize(person);
				person = (User) rpcProtostuffDeserializer.deserialize(data, Object.class);
			}
			PerformanceMonitor.mark("rpcProtostuffSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				rpcProtostuffSerializer.serialize(person, outputStream);
				ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
				person = (User) rpcProtostuffDeserializer.deserialize(inputStream, Object.class);
			}
			PerformanceMonitor.mark("rpcProtostuffDeserializer IO" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				byte[] data = rpcProtobufSerializer.serialize(person);
				person = (User) rpcProtobufDeserializer.deserialize(data, Object.class);
			}
			PerformanceMonitor.mark("rpcProtobufSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				rpcProtobufSerializer.serialize(person, outputStream);
				ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
				person = (User) rpcProtobufDeserializer.deserialize(inputStream, Object.class);
			}
			PerformanceMonitor.mark("rpcProtobufSerializer IO" + i);
		}
		
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}

}
