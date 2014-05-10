package org.danielli.xultimate.core.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;

import org.danielli.xultimate.core.io.support.RpcKryoObjectInput;
import org.danielli.xultimate.core.io.support.RpcKryoObjectOutput;
import org.danielli.xultimate.core.serializer.java.util.SerializerUtils;
import org.danielli.xultimate.core.serializer.kryo.support.ThreadLocalKryoGenerator;
import org.danielli.xultimate.util.StringUtils;
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
	
	@Resource(name = "rpcKryoSerializer")
	private Serializer rpcKryoSerializer;
	
	@Resource(name = "rpcKryoSerializer")
	private Deserializer rpcKryoDeserializer;
	
//	@Test
	public void testBase() {
		User person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		
		byte[] data = rpcKryoSerializer.serialize(person);
		person = (User) rpcKryoDeserializer.deserialize(data, Object.class);
		
		Assert.assertEquals("Daniel Li", person.getName());
		Assert.assertEquals((Integer) 19, person.getAge());
	}
	
//	@Test
	public void testString() throws IOException, ClassNotFoundException {
		PerformanceMonitor.start("SerializerTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 300000; j++) {
				RpcKryoObjectOutput output = new RpcKryoObjectOutput(256, ThreadLocalKryoGenerator.INSTANCE.generate());
				output.writeString("2.5.3");
				RpcKryoObjectInput input = new RpcKryoObjectInput(output.toBytes(), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.close();
				input.readString();
				input.close();
				
			}
			PerformanceMonitor.mark("2.5.3 Kryo writeString" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 300000; j++) {
				RpcKryoObjectOutput output = new RpcKryoObjectOutput(256, ThreadLocalKryoGenerator.INSTANCE.generate());
				output.writeObject("2.5.3");
				RpcKryoObjectInput input = new RpcKryoObjectInput(output.toBytes(), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.close();
				input.readObject();
				input.close();
				
			}
			PerformanceMonitor.mark("2.5.3 Kryo writeObject" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 300000; j++) {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream(256);
				serializeString("2.5.3", outputStream);
				ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(outputStream.toByteArray());
				outputStream.close();
				deserializeString(arrayInputStream);
				arrayInputStream.close();
			}
			PerformanceMonitor.mark("2.5.3 Java writeString" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 300000; j++) {
				RpcKryoObjectOutput output = new RpcKryoObjectOutput(256, ThreadLocalKryoGenerator.INSTANCE.generate());
				output.writeString("org.danielli.xultimate.remoting.service.AccountService");
				RpcKryoObjectInput input = new RpcKryoObjectInput(output.toBytes(), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.close();
				input.readString();
				input.close();
				
			}
			PerformanceMonitor.mark("org.danielli.xultimate.remoting.service.AccountService Kryo writeString" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 300000; j++) {
				RpcKryoObjectOutput output = new RpcKryoObjectOutput(256, ThreadLocalKryoGenerator.INSTANCE.generate());
				output.writeObject("org.danielli.xultimate.remoting.service.AccountService");
				RpcKryoObjectInput input = new RpcKryoObjectInput(output.toBytes(), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.close();
				input.readObject();
				input.close();
				
			}
			PerformanceMonitor.mark("org.danielli.xultimate.remoting.service.AccountService Kryo writeObject" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 300000; j++) {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream(256);
				serializeString("org.danielli.xultimate.remoting.service.AccountService", outputStream);
				ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(outputStream.toByteArray());
				outputStream.close();
				deserializeString(arrayInputStream);
				arrayInputStream.close();
			}
			PerformanceMonitor.mark("org.danielli.xultimate.remoting.service.AccountService Java writeString" + i);
		}
		
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 300000; j++) {
				RpcKryoObjectOutput output = new RpcKryoObjectOutput(256, ThreadLocalKryoGenerator.INSTANCE.generate());
				output.writeString("insertAccount");
				RpcKryoObjectInput input = new RpcKryoObjectInput(output.toBytes(), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.close();
				input.readString();
				input.close();
				
			}
			PerformanceMonitor.mark("insertAccount Kryo writeString" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 300000; j++) {
				RpcKryoObjectOutput output = new RpcKryoObjectOutput(256, ThreadLocalKryoGenerator.INSTANCE.generate());
				output.writeObject("insertAccount");
				RpcKryoObjectInput input = new RpcKryoObjectInput(output.toBytes(), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.close();
				input.readObject();
				input.close();
				
			}
			PerformanceMonitor.mark("insertAccount Kryo writeObject" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 300000; j++) {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream(256);
				serializeString("insertAccount", outputStream);
				ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(outputStream.toByteArray());
				outputStream.close();
				deserializeString(arrayInputStream);
				arrayInputStream.close();
			}
			PerformanceMonitor.mark("insertAccount Java writeString" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
	private void serializeString(String source, OutputStream outputStream) throws SerializerException {
		try {
			if (source == null) {
				outputStream.write(SerializerUtils.encodeInt(-1, false));
			} else {
				byte[] result = StringUtils.getBytesUtf8(source);
				outputStream.write(SerializerUtils.encodeInt(result.length, false));
				outputStream.write(result);
			}
		} catch (IOException e) {
			throw new SerializerException(e.getMessage(), e);
		}
	}
	
	private String deserializeString(InputStream inputStream) throws DeserializerException {
		try {
			byte[] result = new byte[SerializerUtils.INT_BYTE_SIZE];
			inputStream.read(result);
			int length = SerializerUtils.decodeInt(result);
			
			if (length == -1) {
				return null;
			}
			result = new byte[length];
			inputStream.read(result);
			return StringUtils.newStringUtf8(result);
		} catch (IOException e) {
			throw new DeserializerException(e.getMessage(), e);
		}
	}
	
	@Test
	public void test() throws IOException, ClassNotFoundException {
		User person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		
		PerformanceMonitor.start("SerializerTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				byte[] data = rpcKryoSerializer.serialize(person);
				person = (User) rpcKryoDeserializer.deserialize(data, Object.class);
			}
			PerformanceMonitor.mark("rpcKryoSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				RpcKryoObjectOutput output = new RpcKryoObjectOutput(256, ThreadLocalKryoGenerator.INSTANCE.generate());
				output.writeObject(person);
				RpcKryoObjectInput input = new RpcKryoObjectInput(output.toBytes(), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.close();
				person = (User) input.readObject();
				input.close();
			}
			PerformanceMonitor.mark("RpcKryoObjectOutput & RpcKryoObjectInput" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
}
