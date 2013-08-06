package org.danielli.xultimate.core.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.annotation.Resource;

import org.danielli.xultimate.core.serializer.java.util.SerializerUtils;
import org.danielli.xultimate.core.serializer.protostuff.MainProtobufSerializer;
import org.danielli.xultimate.core.serializer.protostuff.MainProtostuffSerializer;
import org.danielli.xultimate.core.serializer.protostuff.RpcProtobufSerializer;
import org.danielli.xultimate.core.serializer.protostuff.RpcProtostuffSerializer;
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

	@Resource(name = "mainProtobufSerializer")
	private MainProtobufSerializer mainProtobufSerializer;
	
	@Resource(name = "mainProtostuffSerializer")
	private MainProtostuffSerializer mainProtostuffSerializer;
	
	@Resource(name = "rpcProtobufSerializer")
	private RpcProtobufSerializer rpcProtobufSerializer;
	
	@Resource(name = "rpcProtostuffSerializer")
	private RpcProtostuffSerializer rpcProtostuffSerializer;
	
	@Resource
	private Serializer protobufSerializer;
	@Resource
	private Deserializer protobufDeserializer;
	@Resource
	private Serializer protostuffSerializer;
	@Resource
	private Deserializer protostuffDeserializer;
	
//	@Test
	public void testBase() {
		Schema<User> schema = SchemaUtils.getSchema(User.class);
		LinkedBuffer linkedBuffer = LinkedBufferUtils.getLinkedBuffer();
		
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
		linkedBuffer = LinkedBufferUtils.getLinkedBuffer();
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
		person = (User) mainProtobufSerializer.deserialize(data, User.class);
		
		Assert.assertEquals("Daniel Li", person.getName());
		Assert.assertEquals((Integer) 19, person.getAge());
		
		person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		
		data = mainProtostuffSerializer.serialize(person);
		person = (User) mainProtostuffSerializer.deserialize(data, User.class);
		
		Assert.assertEquals("Daniel Li", person.getName());
		Assert.assertEquals((Integer) 19, person.getAge());
		
		person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		
		data = rpcProtobufSerializer.serialize(person);
		person = (User) rpcProtobufSerializer.deserialize(data, Object.class);
		
		Assert.assertEquals("Daniel Li", person.getName());
		Assert.assertEquals((Integer) 19, person.getAge());
		
		person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		
		data = rpcProtobufSerializer.serialize(person);
		person = (User) rpcProtobufSerializer.deserialize(data, Object.class);
		
		Assert.assertEquals("Daniel Li", person.getName());
		Assert.assertEquals((Integer) 19, person.getAge());
	}
	
	@Test
	public void test() {
		Schema<User> schema = SchemaUtils.getSchema(User.class);
		LinkedBuffer linkedBuffer = LinkedBufferUtils.getLinkedBuffer();
		
		PerformanceMonitor.start("SerializerTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				linkedBuffer = LinkedBufferUtils.getLinkedBuffer();
				try {
					byte[] data = ProtobufIOUtil.toByteArray(person, schema, linkedBuffer);
					person = new User();
					ProtobufIOUtil.mergeFrom(data, person, schema);
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("protobuf手动优化处理完毕" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				linkedBuffer = LinkedBufferUtils.getLinkedBuffer();
				try {
					byte[] data = ProtostuffIOUtil.toByteArray(person, schema, linkedBuffer);
					person = new User();
					ProtostuffIOUtil.mergeFrom(data, person, schema);
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("protostuff手动优化处理完毕" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				linkedBuffer = LinkedBufferUtils.getLinkedBuffer();
				try {
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					ProtobufIOUtil.writeTo(outputStream, person, SchemaUtils.getSchema(User.class), linkedBuffer);
					ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
					try {
						person = User.class.newInstance();
						ProtobufIOUtil.mergeFrom(inputStream, person, SchemaUtils.getSchema(User.class));
					} catch (Exception e) {
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}  finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("protobuf手动IO处理完毕" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				linkedBuffer = LinkedBufferUtils.getLinkedBuffer();
				try {
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					ProtostuffIOUtil.writeTo(outputStream, person, SchemaUtils.getSchema(User.class), linkedBuffer);
					ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
					try {
						person = User.class.newInstance();
						ProtostuffIOUtil.mergeFrom(inputStream, person, SchemaUtils.getSchema(User.class));
					} catch (Exception e) {
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("protostuff手动IO处理完毕" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				linkedBuffer = LinkedBufferUtils.getLinkedBuffer();
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
			PerformanceMonitor.mark("Protobuf手动处理完毕" + i);
		}

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				linkedBuffer = LinkedBufferUtils.getLinkedBuffer();
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
			PerformanceMonitor.mark("Protostuff手动处理完毕" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				person = (User) mainProtobufSerializer.deserialize(mainProtobufSerializer.serialize(person), User.class);
			}
			PerformanceMonitor.mark("mainProtobufSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				person = (User) mainProtostuffSerializer.deserialize(mainProtostuffSerializer.serialize(person), User.class);
			}
			PerformanceMonitor.mark("mainProtostuffSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				person = (User) rpcProtobufSerializer.deserialize(rpcProtobufSerializer.serialize(person), Object.class);
			}
			PerformanceMonitor.mark("rpcProtobufSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				person = (User) rpcProtostuffSerializer.deserialize(rpcProtostuffSerializer.serialize(person), Object.class);
			}
			PerformanceMonitor.mark("rpcProtostuffSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				protobufDeserializer.deserialize(protobufSerializer.serialize(person), User.class);
			}
			PerformanceMonitor.mark("protobufSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				protostuffDeserializer.deserialize(protostuffSerializer.serialize(person), User.class);
			}
			PerformanceMonitor.mark("protostuffSerializer" + i);
		}
		
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
//	@Test
	public void testString() {
		LinkedBuffer linkedBuffer = LinkedBufferUtils.getLinkedBuffer();
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
					ProtobufIOUtil.mergeFrom(ProtobufIOUtil.toByteArray(value, SchemaUtils.getSchema(String.class), linkedBuffer), value, SchemaUtils.getSchema(String.class));
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("ProtobufIOUtil" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				String value = "abcdefghijklmnopqrstuvwsyz";
				protostuffDeserializer.deserialize(protostuffSerializer.serialize(value), String.class);
			}
			PerformanceMonitor.mark("protostuffDeserializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				String value = "abcdefghijklmnopqrstuvwsyz";
				mainProtostuffSerializer.deserialize(mainProtostuffSerializer.serialize(value), String.class);
			}
			PerformanceMonitor.mark("mainProtostuffSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				String value = "abcdefghijklmnopqrstuvwsyz";
				rpcProtostuffSerializer.deserialize(rpcProtostuffSerializer.serialize(value), String.class);
			}
			PerformanceMonitor.mark("rpcProtostuffSerializer" + i);
		}

		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
//	@Test
	public void testInteger() {
		LinkedBuffer linkedBuffer = LinkedBufferUtils.getLinkedBuffer();
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
					ProtobufIOUtil.mergeFrom(ProtobufIOUtil.toByteArray(value, SchemaUtils.getSchema(Integer.class), linkedBuffer), value, SchemaUtils.getSchema(Integer.class));
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("ProtobufIOUtil" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				Integer value = 10;
				protostuffDeserializer.deserialize(protostuffSerializer.serialize(value), Integer.class);
			}
			PerformanceMonitor.mark("protostuffDeserializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				Integer value = 10;
				mainProtostuffSerializer.deserialize(mainProtostuffSerializer.serialize(value), Integer.class);
			}
			PerformanceMonitor.mark("mainProtostuffSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				Integer value = 10;
				rpcProtostuffSerializer.deserialize(rpcProtostuffSerializer.serialize(value), Integer.class);
			}
			PerformanceMonitor.mark("rpcProtostuffSerializer" + i);
		}
		
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
}
