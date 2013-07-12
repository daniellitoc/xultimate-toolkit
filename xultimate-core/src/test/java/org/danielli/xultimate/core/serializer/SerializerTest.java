package org.danielli.xultimate.core.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.annotation.Resource;

import org.danielli.xultimate.core.serializer.protobuf.MainProtobufSerializer;
import org.danielli.xultimate.core.serializer.protobuf.StandbyProtobufSerializer;
import org.danielli.xultimate.core.serializer.protostuff.MainProtostuffSerializer;
import org.danielli.xultimate.core.serializer.protostuff.StandbyProtostuffSerializer;
import org.danielli.xultimate.core.serializer.util.LinkedBufferUtils;
import org.danielli.xultimate.core.serializer.util.SchemaUtils;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/applicationContext-service-serializer.xml" })
public class SerializerTest {
	
//	private static final Logger LOGGER = LoggerFactory.getLogger(SerializerTest.class);
	
	@Resource(name = "serializingConverter")
	private SerializingConverter serializingConverter;
	
	@Resource(name = "deserializingConverter")
	private DeserializingConverter deserializingConverter;
	
	@Resource
	private StandbyProtobufSerializer standbyProtobufSerializer;
	
	@Resource
	private MainProtobufSerializer mainProtobufSerializer;
	
	@Resource
	private Serializer protobufSerializer;
	
	@Resource
	private Deserializer protobufDeserializer;
	
	@Resource
	private StandbyProtostuffSerializer standbyProtostuffSerializer;
	
	@Resource
	private MainProtostuffSerializer mainProtostuffSerializer;
	
	@Resource
	private Serializer protostuffSerializer;
	
	@Resource
	private Deserializer protostuffDeserializer;
	
	@Test
	public void test() throws IOException {
		Schema<Person> schema = SchemaUtils.getSchema(Person.class);
		
		Person person = new Person();
		person.setName("Daniel Li");
		person.setAge(19);
		LinkedBuffer linkedBuffer = LinkedBufferUtils.getLinkedBuffer();
		try {
			byte[] data = ProtobufIOUtil.toByteArray(person, schema, linkedBuffer);
			person = new Person();
			ProtobufIOUtil.mergeFrom(data, person, schema);
		} finally {
			linkedBuffer.clear();
		}
		Assert.assertEquals("Daniel Li", person.getName());
		Assert.assertEquals((Integer) 19, person.getAge());
		
		person = new Person();
		person.setName("Daniel Li");
		person.setAge(19);
		linkedBuffer = LinkedBufferUtils.getLinkedBuffer();
		try {
			byte[] data = ProtostuffIOUtil.toByteArray(person, schema, linkedBuffer);
			person = new Person();
			ProtostuffIOUtil.mergeFrom(data, person, schema);
		} finally {
			linkedBuffer.clear();
		}
		Assert.assertEquals("Daniel Li", person.getName());
		Assert.assertEquals((Integer) 19, person.getAge());

		PerformanceMonitor.start("SerializerTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new Person();
				person.setName("Daniel Li");
				person.setAge(j);
				byte[] data = serializingConverter.convert(person);
				person = (Person) deserializingConverter.convert(data);
			}
			PerformanceMonitor.mark("Java处理完毕" + i);
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new Person();
				person.setName("Daniel Li");
				person.setAge(i);
				linkedBuffer = LinkedBufferUtils.getLinkedBuffer();
				try {
					byte[] data = ProtobufIOUtil.toByteArray(person, schema, linkedBuffer);
					person = new Person();
					ProtobufIOUtil.mergeFrom(data, person, schema);
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("protobuf手动优化处理完毕" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new Person();
				person.setName("Daniel Li");
				person.setAge(i);
				linkedBuffer = LinkedBufferUtils.getLinkedBuffer();
				try {
					byte[] data = ProtostuffIOUtil.toByteArray(person, schema, linkedBuffer);
					person = new Person();
					ProtostuffIOUtil.mergeFrom(data, person, schema);
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("protostuff手动优化处理完毕" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new Person();
				person.setName("Daniel Li");
				person.setAge(i);
				linkedBuffer = LinkedBufferUtils.getLinkedBuffer();
				try {
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					ProtobufIOUtil.writeTo(outputStream, person, SchemaUtils.getSchema(Person.class), linkedBuffer);
					ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
					try {
						person = Person.class.newInstance();
						ProtobufIOUtil.mergeFrom(inputStream, person, SchemaUtils.getSchema(Person.class));
					} catch (Exception e) {
						
					}
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("protobuf手动IO处理完毕" + i);
		}

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new Person();
				person.setName("Daniel Li");
				person.setAge(i);
				linkedBuffer = LinkedBufferUtils.getLinkedBuffer();
				try {
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					ProtostuffIOUtil.writeTo(outputStream, person, SchemaUtils.getSchema(Person.class), linkedBuffer);
					ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
					try {
						person = Person.class.newInstance();
						ProtostuffIOUtil.mergeFrom(inputStream, person, SchemaUtils.getSchema(Person.class));
					} catch (Exception e) {
						
					}
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("protostuff手动IO处理完毕" + i);
		}
	
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new Person();
				person.setName("Daniel Li");
				person.setAge(i);
				byte[] data = standbyProtobufSerializer.serialize(person);
				person = standbyProtobufSerializer.deserialize(data, Person.class);
			}
			PerformanceMonitor.mark("standyProtobufSerializer处理完毕" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new Person();
				person.setName("Daniel Li");
				person.setAge(i);
				byte[] data = standbyProtostuffSerializer.serialize(person);
				person = standbyProtostuffSerializer.deserialize(data, Person.class);
			}
			PerformanceMonitor.mark("standbyProtostuffSerializer处理完毕" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new Person();
				person.setName("Daniel Li");
				person.setAge(i);
				byte[] data = mainProtobufSerializer.serialize(person);
				person = mainProtobufSerializer.deserialize(data, Person.class);
			}
			PerformanceMonitor.mark("mainProtobufSerializer处理完毕" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new Person();
				person.setName("Daniel Li");
				person.setAge(i);
				byte[] data = mainProtostuffSerializer.serialize(person);
				person = mainProtostuffSerializer.deserialize(data, Person.class);
			}
			PerformanceMonitor.mark("mainProtostuffSerializer处理完毕" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new Person();
				person.setName("Daniel Li");
				person.setAge(i);
				byte[] data = protobufSerializer.serialize(person);
				person = protobufDeserializer.deserialize(data, Person.class);
			}
			PerformanceMonitor.mark("ProtobufSerializer处理完毕" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new Person();
				person.setName("Daniel Li");
				person.setAge(i);
				byte[] data = protostuffSerializer.serialize(person);
				person = protostuffDeserializer.deserialize(data, Person.class);
			}
			PerformanceMonitor.mark("ProtostuffSerializer处理完毕" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new Person();
				person.setName("Daniel Li");
				person.setAge(i);
				linkedBuffer = LinkedBufferUtils.getLinkedBuffer();
				try {
					byte[] data = ProtobufIOUtil.toByteArray(person, SchemaUtils.getSchema(Person.class), linkedBuffer);
					try {
						person = Person.class.newInstance();
						ProtobufIOUtil.mergeFrom(data, person, SchemaUtils.getSchema(Person.class));
					} catch (Exception e) {
						
					}
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("Protobuf手动处理完毕" + i);
		}

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new Person();
				person.setName("Daniel Li");
				person.setAge(i);
				linkedBuffer = LinkedBufferUtils.getLinkedBuffer();
				try {
					byte[] data = ProtostuffIOUtil.toByteArray(person, SchemaUtils.getSchema(Person.class), linkedBuffer);
					try {
						person = Person.class.newInstance();
						ProtostuffIOUtil.mergeFrom(data, person, SchemaUtils.getSchema(Person.class));
					} catch (Exception e) {
						
					}
				} finally {
					linkedBuffer.clear();
				}
			}
			PerformanceMonitor.mark("Protostuff手动处理完毕" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
}
