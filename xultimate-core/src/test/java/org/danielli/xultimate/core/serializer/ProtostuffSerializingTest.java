package org.danielli.xultimate.core.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.annotation.Resource;

import org.danielli.xultimate.core.io.support.RpcProtostuffObjectInput;
import org.danielli.xultimate.core.io.support.RpcProtostuffObjectOutput;
import org.danielli.xultimate.core.serializer.java.util.SerializerUtils;
import org.danielli.xultimate.core.serializer.kryo.support.ThreadLocalKryoGenerator;
import org.danielli.xultimate.core.serializer.protostuff.util.LinkedBufferUtils;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-service-serializer.xml" })
public class ProtostuffSerializingTest {
	
	@Resource(name = "rpcProtostuffSerializer")
	private Serializer rpcProtostuffSerializer;
	
	@Resource(name = "rpcProtostuffSerializer")
	private Deserializer rpcProtostuffDeserializer;
	
//	@Test
	public void testBase() {
		User person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		
		byte[] data = rpcProtostuffSerializer.serialize(person);
		person = (User) rpcProtostuffDeserializer.deserialize(data, Object.class);
		
		Assert.assertEquals("Daniel Li", person.getName());
		Assert.assertEquals((Integer) 19, person.getAge());
		
		Schema<User> schema = RuntimeSchema.getSchema(User.class);
		LinkedBuffer linkedBuffer = LinkedBufferUtils.getCurrentLinkedBuffer(256);
		
		person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		try {
			data = ProtostuffIOUtil.toByteArray(person, schema, linkedBuffer);
			person = new User();
			ProtostuffIOUtil.mergeFrom(data, person, schema);
		} finally {
			linkedBuffer.clear();
		}
		Assert.assertEquals("Daniel Li", person.getName());
		Assert.assertEquals((Integer) 19, person.getAge());
	}
	
//	@Test
	public void testInteger() throws IOException, ClassNotFoundException {
		LinkedBuffer linkedBuffer = LinkedBufferUtils.getCurrentLinkedBuffer(256);
		PerformanceMonitor.start("Test3");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				SerializerUtils.decodeInt(SerializerUtils.encodeInt(10, true));
			}
			PerformanceMonitor.mark("coder" + i);
		}
		
		Schema<Integer> integerSchema = RuntimeSchema.getSchema(Integer.class);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				try {
					Integer value = 10;
					ProtostuffIOUtil.mergeFrom(ProtostuffIOUtil.toByteArray(value, integerSchema, linkedBuffer), value, integerSchema);
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("ProtostuffIOUtil" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				Integer value = 10;
				rpcProtostuffDeserializer.deserialize(rpcProtostuffSerializer.serialize(value), Integer.class);
			}
			PerformanceMonitor.mark("rpcProtostuffSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				Integer value = 10;
				RpcProtostuffObjectOutput output = new RpcProtostuffObjectOutput(256, LinkedBufferUtils.getCurrentLinkedBuffer(256), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.writeObject(value);
				RpcProtostuffObjectInput input = new RpcProtostuffObjectInput(output.toBytes(), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.close();
				value = (Integer) input.readObject();
				input.close();
			}
			PerformanceMonitor.mark("RpcProtostuffObjectInput readObject" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				Integer value = 10;
				RpcProtostuffObjectOutput output = new RpcProtostuffObjectOutput(256, LinkedBufferUtils.getCurrentLinkedBuffer(256), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.writeInt(value, true);
				RpcProtostuffObjectInput input = new RpcProtostuffObjectInput(output.toBytes(), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.close();
				value = input.readInt(true);
				input.close();
			}
			PerformanceMonitor.mark("RpcProtostuffObjectInput readInt" + i);
		}
		
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
//	@Test
	public void testString() throws IOException, ClassNotFoundException {
		LinkedBuffer linkedBuffer = LinkedBufferUtils.getCurrentLinkedBuffer(256);
		PerformanceMonitor.start("Test2");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				StringUtils.newStringUtf8(StringUtils.getBytesUtf8("abcdefghijklmnopqrstuvwsyz"));
			}
			PerformanceMonitor.mark("coder" + i);
		}
		
		Schema<String> integerSchema = RuntimeSchema.getSchema(String.class);
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				try {
					String value = "abcdefghijklmnopqrstuvwsyz";
					ProtostuffIOUtil.mergeFrom(ProtostuffIOUtil.toByteArray(value, integerSchema, linkedBuffer), value, integerSchema);
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("ProtostuffIOUtil" + i);
		}

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				String value = "abcdefghijklmnopqrstuvwsyz";
				rpcProtostuffDeserializer.deserialize(rpcProtostuffSerializer.serialize(value), Integer.class);
			}
			PerformanceMonitor.mark("rpcProtobufSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				String value = "abcdefghijklmnopqrstuvwsyz";
				RpcProtostuffObjectOutput output = new RpcProtostuffObjectOutput(256, LinkedBufferUtils.getCurrentLinkedBuffer(256), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.writeObject(value);
				RpcProtostuffObjectInput input = new RpcProtostuffObjectInput(output.toBytes(), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.close();
				value = (String) input.readObject();
				input.close();
			}
			PerformanceMonitor.mark("RpcProtostuffObjectInput readObject" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				String value = "abcdefghijklmnopqrstuvwsyz";
				RpcProtostuffObjectOutput output = new RpcProtostuffObjectOutput(256, LinkedBufferUtils.getCurrentLinkedBuffer(256), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.writeString(value);
				RpcProtostuffObjectInput input = new RpcProtostuffObjectInput(output.toBytes(), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.close();
				value = input.readString();
				input.close();
			}
			PerformanceMonitor.mark("RpcProtostuffObjectInput readString" + i);
		}

		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
	@Test
	public void test() throws IOException, ClassNotFoundException {
		User person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		
		Schema<User> schema = RuntimeSchema.getSchema(User.class);
		LinkedBuffer linkedBuffer = LinkedBufferUtils.getCurrentLinkedBuffer(256);
		
		PerformanceMonitor.start("SerializerTest");
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				linkedBuffer = LinkedBufferUtils.getCurrentLinkedBuffer(256);
				try {
					byte[] data = ProtostuffIOUtil.toByteArray(person, schema, linkedBuffer);
					person = new User();
					ProtostuffIOUtil.mergeFrom(data, person, schema);
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("ProtostuffIOUtil" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				try {
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					ProtostuffIOUtil.writeTo(outputStream, person, schema, linkedBuffer);
					ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
					person = schema.newMessage();
					ProtostuffIOUtil.mergeFrom(inputStream, person, schema);
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("ProtostuffIOUtil IO" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				byte[] data = rpcProtostuffSerializer.serialize(person);
				person = (User) rpcProtostuffDeserializer.deserialize(data, Object.class);
			}
			PerformanceMonitor.mark("rpcProtostuffSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				RpcProtostuffObjectOutput output = new RpcProtostuffObjectOutput(256, LinkedBufferUtils.getCurrentLinkedBuffer(256), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.writeObject(person);
				RpcProtostuffObjectInput input = new RpcProtostuffObjectInput(output.toBytes(), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.close();
				person = (User) input.readObject();
				input.close();
			}
			PerformanceMonitor.mark("RpcProtostuffObjectOutput & RpcProtostuffObjectInput" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
}
