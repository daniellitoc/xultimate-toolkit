package org.danielli.xultimate.core.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;

import org.danielli.xultimate.core.serializer.java.util.SerializerUtils;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-service-serializer.xml" })
public class KryoSerializingTest {
	
	@Resource(name = "mainKryoSerializerProxy")
	private Serializer mainKryoSerializer;
	
	@Resource(name = "mainKryoDeserializerProxy")
	private Deserializer mainKryoDeserializer;
	
	@Resource(name = "rpcKryoSerializerProxy")
	private Serializer rpcKryoSerializer;
	
	@Resource(name = "rpcKryoDeserializerProxy")
	private Deserializer rpcKryoDeserializer;
	
//	@Test
	public void testBase() {
		User person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		
		byte[] data = mainKryoSerializer.serialize(person);
		person = (User) mainKryoDeserializer.deserialize(data, User.class);
		
		Assert.assertEquals("Daniel Li", person.getName());
		Assert.assertEquals((Integer) 19, person.getAge());
		
		person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		
		data = rpcKryoSerializer.serialize(person);
		person = (User) rpcKryoDeserializer.deserialize(data, Object.class);
		
		Assert.assertEquals("Daniel Li", person.getName());
		Assert.assertEquals((Integer) 19, person.getAge());
	}
	
	@Test
	public void testString() throws IOException {
		PerformanceMonitor.start("SerializerTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 300000; j++) {
				Output output = new Output(1024);
				output.writeString("2.5.3");
				Input input = new Input(output.toBytes());
				output.close();
				input.readString();
				input.close();
				
			}
			PerformanceMonitor.mark("2.5.3 Kryo" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 300000; j++) {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
				serializeString("2.5.3", outputStream);
				ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(outputStream.toByteArray());
				outputStream.close();
				deserializeString(arrayInputStream);
				arrayInputStream.close();
			}
			PerformanceMonitor.mark("2.5.3 Java" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 300000; j++) {
				Output output = new Output(1024);
				output.writeString("org.danielli.xultimate.remoting.service.AccountService");
				Input input = new Input(output.toBytes());
				output.close();
				input.readString();
				input.close();
			}
			PerformanceMonitor.mark("org.danielli.xultimate.remoting.service.AccountService Kryo" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 300000; j++) {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
				serializeString("org.danielli.xultimate.remoting.service.AccountService", outputStream);
				ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(outputStream.toByteArray());
				outputStream.close();
				deserializeString(arrayInputStream);
				arrayInputStream.close();
			}
			PerformanceMonitor.mark("org.danielli.xultimate.remoting.service.AccountService Java" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 300000; j++) {
				Output output = new Output(1024);
				output.writeString("insertAccount");
				Input input = new Input(output.toBytes());
				output.close();
				input.readString();
				input.close();
			}
			PerformanceMonitor.mark("insertAccount Kryo" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 300000; j++) {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
				serializeString("insertAccount", outputStream);
				ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(outputStream.toByteArray());
				outputStream.close();
				deserializeString(arrayInputStream);
				arrayInputStream.close();
			}
			PerformanceMonitor.mark("insertAccount Java" + i);
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
	
//	@Test
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
				person = mainKryoDeserializer.deserialize(data, User.class);
			}
			PerformanceMonitor.mark("mainKryoSerializer" + i);
		}
		
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
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
}
