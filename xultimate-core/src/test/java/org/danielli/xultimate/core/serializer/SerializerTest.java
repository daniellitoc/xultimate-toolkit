package org.danielli.xultimate.core.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.danielli.xultimate.core.io.support.JavaObjectInput;
import org.danielli.xultimate.core.io.support.JavaObjectOutput;
import org.danielli.xultimate.core.io.support.RpcKryoObjectInput;
import org.danielli.xultimate.core.io.support.RpcKryoObjectOutput;
import org.danielli.xultimate.core.io.support.RpcProtobufObjectInput;
import org.danielli.xultimate.core.io.support.RpcProtobufObjectOutput;
import org.danielli.xultimate.core.io.support.RpcProtostuffObjectInput;
import org.danielli.xultimate.core.io.support.RpcProtostuffObjectOutput;
import org.danielli.xultimate.core.serializer.kryo.support.ThreadLocalKryoGenerator;
import org.danielli.xultimate.core.serializer.protostuff.util.LinkedBufferUtils;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext-service-serializer.xml" })
public class SerializerTest {
	
	@Test
	public void testObject() throws IOException, ClassNotFoundException {
		PerformanceMonitor.start("SerializerTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				JavaObjectOutput output = new JavaObjectOutput(256);
				output.writeObject(person);
				JavaObjectInput input = new JavaObjectInput(output.toBytes());
				output.close();
				person = (User) input.readObject();
				input.close();
			}
			PerformanceMonitor.mark("JavaObjectOutput & JavaObjectInput" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				RpcKryoObjectOutput output = new RpcKryoObjectOutput(256, ThreadLocalKryoGenerator.INSTANCE.generate());
				output.writeObject(person);
				RpcKryoObjectInput input = new RpcKryoObjectInput(output.toBytes(), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.close();
				person = (User) input.readObject();
				input.close();
			}
			PerformanceMonitor.mark("RpcKryoObjectOutput & RpcKryoObjectInput" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				RpcProtobufObjectOutput output = new RpcProtobufObjectOutput(256, LinkedBufferUtils.getCurrentLinkedBuffer(256), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.writeObject(person);
				RpcProtobufObjectInput input = new RpcProtobufObjectInput(output.toBytes(), LinkedBufferUtils.getCurrentLinkedBuffer(256), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.close();
				person = (User) input.readObject();
				input.close();
			}
			PerformanceMonitor.mark("RpcProtobufObjectOutput & RpcProtobufObjectInput" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(i);
				RpcProtostuffObjectOutput output = new RpcProtostuffObjectOutput(256, LinkedBufferUtils.getCurrentLinkedBuffer(256), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.writeObject(person);
				RpcProtostuffObjectInput input = new RpcProtostuffObjectInput(output.toBytes(), LinkedBufferUtils.getCurrentLinkedBuffer(256), ThreadLocalKryoGenerator.INSTANCE.generate());
				output.close();
				person = (User) input.readObject();
				input.close();
			}
			PerformanceMonitor.mark("RpcProtostuffObjectOutput & RpcProtostuffObjectInput" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				Hessian2Output hessian2Output = new Hessian2Output(outputStream);
				hessian2Output.writeObject(person);
				hessian2Output.close();
				ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
				Hessian2Input hessian2Input = new Hessian2Input(inputStream);
				person = (User) hessian2Input.readObject();
				hessian2Input.close();
			}
			PerformanceMonitor.mark("Hessian2 IO" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				User person = new User();
				person.setName("Daniel Li");
				person.setAge(j);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				HessianOutput hessianOutput = new HessianOutput(outputStream);
				hessianOutput.writeObject(person);
				hessianOutput.close();
				ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
				HessianInput hessianInput = new HessianInput(inputStream);
				person = (User) hessianInput.readObject();
				hessianInput.close();
			}
			PerformanceMonitor.mark("Hessian IO" + i);
		}
		
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}

}
