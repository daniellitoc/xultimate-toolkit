package org.danielli.xultimate.core.serializer;

import java.io.IOException;

import javax.annotation.Resource;

import org.danielli.xultimate.core.io.support.JavaObjectInput;
import org.danielli.xultimate.core.io.support.JavaObjectOutput;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-service-serializer.xml" })
public class JavaSerializingTest {
	
	@Resource(name = "javaObjectSerializer")
	private Serializer javaObjectSerializer;
	
	@Resource(name = "javaObjectSerializer")
	private Deserializer javaObjectDeserializer;
	
//	@Test
	public void testBase() {
		User person = new User();
		person.setName("Daniel Li");
		person.setAge(19);
		
		byte[] data = javaObjectSerializer.serialize(person);
		person = (User) javaObjectDeserializer.deserialize(data, User.class);
		
		Assert.assertEquals("Daniel Li", person.getName());
		Assert.assertEquals((Integer) 19, person.getAge());
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
				byte[] data = javaObjectSerializer.serialize(person);
				person = (User) javaObjectDeserializer.deserialize(data, Object.class);
			}
			PerformanceMonitor.mark("javaObjectSerializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				JavaObjectOutput output = new JavaObjectOutput(256);
				output.writeObject(person);
				JavaObjectInput input = new JavaObjectInput(output.toBytes());
				output.close();
				person = (User) input.readObject();
				input.close();
			}
			PerformanceMonitor.mark("JavaObjectOutput & JavaObjectInput" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
}
