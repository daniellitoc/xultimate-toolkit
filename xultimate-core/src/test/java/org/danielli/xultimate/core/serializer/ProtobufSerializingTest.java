package org.danielli.xultimate.core.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.annotation.Resource;

import org.danielli.xultimate.core.io.support.RpcProtobufObjectInput;
import org.danielli.xultimate.core.io.support.RpcProtobufObjectOutput;
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
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-service-serializer.xml" })
public class ProtobufSerializingTest {
	
	@Resource(name = "rpcProtobufSerializer")
	private Serializer rpcProtobufSerializer;
	
	@Resource(name = "rpcProtobufSerializer")
	private Deserializer rpcProtobufDeserializer;
	
//	@Test
	public void testBase() {
		User person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		
		byte[] data = rpcProtobufSerializer.serialize(person);
		person = (User) rpcProtobufDeserializer.deserialize(data, Object.class);
		
		Assert.assertEquals("Daniel Li", person.getName());
		Assert.assertEquals((Integer) 19, person.getAge());
		
		Schema<User> schema = RuntimeSchema.getSchema(User.class);
		LinkedBuffer linkedBuffer = LinkedBufferUtils.getCurrentLinkedBuffer(256);
		
		person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		try {
			data = ProtobufIOUtil.toByteArray(person, schema, linkedBuffer);
			person = new User();
			ProtobufIOUtil.mergeFrom(data, person, schema);
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
					ProtobufIOUtil.mergeFrom(ProtobufIOUtil.toByteArray(value, integerSchema, linkedBuffer), value, integerSchema);
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("ProtobufIOUtil" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				Integer value = 10;
				rpcProtobufDeserializer.deserialize(rpcProtobufSerializer.serialize(value), Integer.class);
			}
			PerformanceMonitor.mark("rpcProtobufSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				Integer value = 10;
				RpcProtobufObjectOutput output = new RpcProtobufObjectOutput(256, LinkedBufferUtils.getCurrentLinkedBuffer(256), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.writeObject(value);
				RpcProtobufObjectInput input = new RpcProtobufObjectInput(output.toBytes(), LinkedBufferUtils.getCurrentLinkedBuffer(256), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.close();
				value = (Integer) input.readObject();
				input.close();
			}
			PerformanceMonitor.mark("RpcProtobufObjectInput readObject" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				Integer value = 10;
				RpcProtobufObjectOutput output = new RpcProtobufObjectOutput(256, LinkedBufferUtils.getCurrentLinkedBuffer(256), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.writeInt(value, true);
				RpcProtobufObjectInput input = new RpcProtobufObjectInput(output.toBytes(), LinkedBufferUtils.getCurrentLinkedBuffer(256), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.close();
				value = input.readInt(true);
				input.close();
			}
			PerformanceMonitor.mark("RpcProtobufObjectInput readInt" + i);
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
					ProtobufIOUtil.mergeFrom(ProtobufIOUtil.toByteArray(value, integerSchema, linkedBuffer), value, integerSchema);
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("ProtobufIOUtil" + i);
		}

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				String value = "abcdefghijklmnopqrstuvwsyz";
				rpcProtobufDeserializer.deserialize(rpcProtobufSerializer.serialize(value), Integer.class);
			}
			PerformanceMonitor.mark("rpcProtobufSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				String value = "abcdefghijklmnopqrstuvwsyz";
				RpcProtobufObjectOutput output = new RpcProtobufObjectOutput(256, LinkedBufferUtils.getCurrentLinkedBuffer(256), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.writeObject(value);
				RpcProtobufObjectInput input = new RpcProtobufObjectInput(output.toBytes(), LinkedBufferUtils.getCurrentLinkedBuffer(256), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.close();
				value = (String) input.readObject();
				input.close();
			}
			PerformanceMonitor.mark("RpcProtobufObjectInput readObject" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				String value = "abcdefghijklmnopqrstuvwsyz";
				RpcProtobufObjectOutput output = new RpcProtobufObjectOutput(256, LinkedBufferUtils.getCurrentLinkedBuffer(256), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.writeString(value);
				RpcProtobufObjectInput input = new RpcProtobufObjectInput(output.toBytes(), LinkedBufferUtils.getCurrentLinkedBuffer(256), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.close();
				value = input.readString();
				input.close();
			}
			PerformanceMonitor.mark("RpcProtobufObjectInput readString" + i);
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
				try {
					byte[] data = ProtobufIOUtil.toByteArray(person, schema, linkedBuffer);
					person = new User();
					ProtobufIOUtil.mergeFrom(data, person, schema);
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("ProtobufIOUtil" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				try {
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					ProtobufIOUtil.writeTo(outputStream, person, schema, linkedBuffer);
					ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
					person = schema.newMessage();
					ProtobufIOUtil.mergeFrom(inputStream, person, schema);
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("ProtobufIOUtil IO" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				byte[] data = rpcProtobufSerializer.serialize(person);
				person = (User) rpcProtobufDeserializer.deserialize(data, Object.class);
			}
			PerformanceMonitor.mark("rpcProtobufSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				RpcProtobufObjectOutput output = new RpcProtobufObjectOutput(256, LinkedBufferUtils.getCurrentLinkedBuffer(256), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.writeObject(person);
				RpcProtobufObjectInput input = new RpcProtobufObjectInput(output.toBytes(), LinkedBufferUtils.getCurrentLinkedBuffer(256), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.close();
				person = (User) input.readObject();
				input.close();
			}
			PerformanceMonitor.mark("RpcProtobufObjectOutput & RpcProtobufObjectInput" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
}
