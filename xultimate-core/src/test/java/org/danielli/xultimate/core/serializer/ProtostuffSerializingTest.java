package org.danielli.xultimate.core.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.annotation.Resource;

import org.danielli.xultimate.core.serializer.java.util.SerializerUtils;
import org.danielli.xultimate.core.serializer.protostuff.util.LinkedBufferUtils;
import org.danielli.xultimate.core.serializer.protostuff.util.SchemaUtils;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-service-serializer.xml" })
public class ProtostuffSerializingTest {

	@Resource(name = "mainProtobufSerializerProxy")
	private Serializer mainProtobufSerializer;
	
	@Resource(name = "mainProtobufDeserializerProxy")
	private Deserializer mainProtobufDeserializer;
	
	@Resource(name = "rpcProtobufSerializerProxy")
	private Serializer rpcProtobufSerializer;
	
	@Resource(name = "rpcProtobufDeserializerProxy")
	private Deserializer rpcProtobufDeserializer;
	
	@Resource(name = "mainProtostuffSerializerProxy")
	private Serializer mainProtostuffSerializer;
	
	@Resource(name = "mainProtostuffDeserializerProxy")
	private Deserializer mainProtostuffDeserializer;
	
	@Resource(name = "rpcProtostuffSerializerProxy")
	private Serializer rpcProtostuffSerializer;
	
	@Resource(name = "rpcProtostuffDeserializerProxy")
	private Deserializer rpcProtostuffDeserializer;
	
//	@Test
	public void testBase() {
		Schema<User> schema = SchemaUtils.getSchema(User.class);
		LinkedBuffer linkedBuffer = LinkedBufferUtils.getCurrentLinkedBuffer(10 * 1024);
		
		User person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		try {
			byte[] data = ProtobufIOUtil.toByteArray(person, schema, linkedBuffer);
			person = new User();
			ProtobufIOUtil.mergeFrom(data, person, schema);
		} finally {
			linkedBuffer.clear();
		}
		Assert.assertEquals("Daniel Li", person.getName());
		Assert.assertEquals((Integer) 19, person.getAge());
		
		person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		linkedBuffer = LinkedBufferUtils.getCurrentLinkedBuffer(10 * 1024);
		try {
			byte[] data = ProtostuffIOUtil.toByteArray(person, schema, linkedBuffer);
			person = new User();
			ProtostuffIOUtil.mergeFrom(data, person, schema);
		} finally {
			linkedBuffer.clear();
		}
		Assert.assertEquals("Daniel Li", person.getName());
		Assert.assertEquals((Integer) 19, person.getAge());
		
		person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		
		byte[] data = mainProtobufSerializer.serialize(person);
		person = (User) mainProtobufDeserializer.deserialize(data, User.class);
		
		Assert.assertEquals("Daniel Li", person.getName());
		Assert.assertEquals((Integer) 19, person.getAge());
		
		person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		
		data = mainProtostuffSerializer.serialize(person);
		person = (User) mainProtostuffDeserializer.deserialize(data, User.class);
		
		Assert.assertEquals("Daniel Li", person.getName());
		Assert.assertEquals((Integer) 19, person.getAge());
		
		person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		
		data = rpcProtobufSerializer.serialize(person);
		person = (User) rpcProtobufDeserializer.deserialize(data, Object.class);
		
		Assert.assertEquals("Daniel Li", person.getName());
		Assert.assertEquals((Integer) 19, person.getAge());
		
		person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		
		data = rpcProtostuffSerializer.serialize(person);
		person = (User) rpcProtostuffDeserializer.deserialize(data, Object.class);
		
		Assert.assertEquals("Daniel Li", person.getName());
		Assert.assertEquals((Integer) 19, person.getAge());
	}
	
//	@Test
	public void testInteger() {
		LinkedBuffer linkedBuffer = LinkedBufferUtils.getCurrentLinkedBuffer(10 * 1024);
		PerformanceMonitor.start("Test3");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10000; j++) {
				SerializerUtils.decodeInt(SerializerUtils.encodeInt(10, true));
			}
			PerformanceMonitor.mark("coder" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				try {
					Integer value = 10;
					ProtostuffIOUtil.mergeFrom(ProtostuffIOUtil.toByteArray(value, SchemaUtils.getSchema(Integer.class), linkedBuffer), value, SchemaUtils.getSchema(Integer.class));
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("ProtostuffIOUtil" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				Integer value = 10;
				mainProtostuffDeserializer.deserialize(mainProtostuffSerializer.serialize(value), Integer.class);
			}
			PerformanceMonitor.mark("mainProtostuffDeserializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				Integer value = 10;
				rpcProtostuffDeserializer.deserialize(rpcProtostuffSerializer.serialize(value), Integer.class);
			}
			PerformanceMonitor.mark("rpcProtostuffDeserializer" + i);
		}
		
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
//	@Test
	public void testString() {
		LinkedBuffer linkedBuffer = LinkedBufferUtils.getCurrentLinkedBuffer(10 * 1024);
		PerformanceMonitor.start("Test2");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				StringUtils.newStringUtf8(StringUtils.getBytesUtf8("abcdefghijklmnopqrstuvwsyz"));
			}
			PerformanceMonitor.mark("coder" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				try {
					String value = "abcdefghijklmnopqrstuvwsyz";
					ProtostuffIOUtil.mergeFrom(ProtostuffIOUtil.toByteArray(value, SchemaUtils.getSchema(String.class), linkedBuffer), value, SchemaUtils.getSchema(String.class));
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("ProtostuffIOUtil" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				String value = "abcdefghijklmnopqrstuvwsyz";
				mainProtostuffDeserializer.deserialize(mainProtostuffSerializer.serialize(value), String.class);
			}
			PerformanceMonitor.mark("mainProtostuffDeserializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				String value = "abcdefghijklmnopqrstuvwsyz";
				rpcProtostuffDeserializer.deserialize(rpcProtostuffSerializer.serialize(value), String.class);
			}
			PerformanceMonitor.mark("rpcProtostuffDeserializer" + i);
		}

		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
	@Test
	public void test() {
		Schema<User> schema = SchemaUtils.getSchema(User.class);
		LinkedBuffer linkedBuffer = LinkedBufferUtils.getCurrentLinkedBuffer(10 * 1024);
		
		PerformanceMonitor.start("SerializerTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				linkedBuffer = LinkedBufferUtils.getCurrentLinkedBuffer(10 * 1024);
				try {
					byte[] data = ProtobufIOUtil.toByteArray(person, schema, linkedBuffer);
					person = new User();
					ProtobufIOUtil.mergeFrom(data, person, schema);
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("protobuf 优化" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				linkedBuffer = LinkedBufferUtils.getCurrentLinkedBuffer(10 * 1024);
				try {
					byte[] data = ProtostuffIOUtil.toByteArray(person, schema, linkedBuffer);
					person = new User();
					ProtostuffIOUtil.mergeFrom(data, person, schema);
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("protostuff 优化" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				linkedBuffer = LinkedBufferUtils.getCurrentLinkedBuffer(10 * 1024);
				try {
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					ProtobufIOUtil.writeTo(outputStream, person, SchemaUtils.getSchema(User.class), linkedBuffer);
					ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
					person = User.class.newInstance();
					ProtobufIOUtil.mergeFrom(inputStream, person, SchemaUtils.getSchema(User.class));
				} catch (Exception e) {
					e.printStackTrace();
				}  finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("protobuf IO 优化" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				linkedBuffer = LinkedBufferUtils.getCurrentLinkedBuffer(10 * 1024);
				try {
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					ProtostuffIOUtil.writeTo(outputStream, person, SchemaUtils.getSchema(User.class), linkedBuffer);
					ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
					person = User.class.newInstance();
					ProtostuffIOUtil.mergeFrom(inputStream, person, SchemaUtils.getSchema(User.class));
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("protostuff IO 优化" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				linkedBuffer = LinkedBufferUtils.getCurrentLinkedBuffer(10 * 1024);
				try {
					byte[] data = ProtobufIOUtil.toByteArray(person, SchemaUtils.getSchema(User.class), linkedBuffer);
					try {
						person = User.class.newInstance();
						ProtobufIOUtil.mergeFrom(data, person, SchemaUtils.getSchema(User.class));
					} catch (Exception e) {
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("protobuf" + i);
		}

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				linkedBuffer = LinkedBufferUtils.getCurrentLinkedBuffer(10 * 1024);
				try {
					byte[] data = ProtostuffIOUtil.toByteArray(person, SchemaUtils.getSchema(User.class), linkedBuffer);
					try {
						person = User.class.newInstance();
						ProtostuffIOUtil.mergeFrom(data, person, SchemaUtils.getSchema(User.class));
					} catch (Exception e) {
						
					}
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("protostuff" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				person = (User) mainProtobufDeserializer.deserialize(mainProtobufSerializer.serialize(person), User.class);
			}
			PerformanceMonitor.mark("mainProtobufSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				mainProtobufSerializer.serialize(person, outputStream);
				ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
				person = (User) mainProtobufDeserializer.deserialize(inputStream, User.class);
			}
			PerformanceMonitor.mark("mainProtobufSerializer IO" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				person = (User) mainProtostuffDeserializer.deserialize(mainProtostuffSerializer.serialize(person), User.class);
			}
			PerformanceMonitor.mark("mainProtostuffSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				mainProtostuffSerializer.serialize(person, outputStream);
				ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
				person = (User) mainProtostuffDeserializer.deserialize(inputStream, User.class);
			}
			PerformanceMonitor.mark("mainProtostuffSerializer IO" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				person = (User) rpcProtobufDeserializer.deserialize(rpcProtobufSerializer.serialize(person), Object.class);
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
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				person = (User) rpcProtostuffDeserializer.deserialize(rpcProtostuffSerializer.serialize(person), Object.class);
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
			PerformanceMonitor.mark("rpcProtostuffSerializer IO" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
}
