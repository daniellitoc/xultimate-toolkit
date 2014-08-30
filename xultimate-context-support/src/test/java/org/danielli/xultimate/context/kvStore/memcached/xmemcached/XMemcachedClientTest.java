package org.danielli.xultimate.context.kvStore.memcached.xmemcached;

import java.util.Arrays;
import java.util.Map;

import javax.annotation.Resource;

import org.danielli.xultimate.context.kvStore.memcached.CacheService;
import org.danielli.xultimate.core.serializer.Deserializer;
import org.danielli.xultimate.core.serializer.Serializer;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/kvStore/memcached/applicationContext-service-memcached.xml", "classpath:/kvStore/applicationContext-service-serializer.xml"})
public class XMemcachedClientTest {

	private final Logger LOGGER = LoggerFactory.getLogger(XMemcachedClientTest.class);
	
	@Resource(name = "xMemcachedClient")
	private XMemcachedClient xMemcachedClient;
	
	@Resource(name = "rpcProtostuffSerializer")
	private Serializer rpcProtostuffSerializer;
	
	@Resource(name = "rpcProtostuffSerializer")
	private Deserializer rpcProtostuffDeserializer;
	
	@Resource
	private SerializingTranscoder serializingTranscoder;
	@Resource
	private net.rubyeye.xmemcached.transcoders.SerializingTranscoder defaultTranscoder;
	
	@Resource
	private CacheService cacheService;
	
//	@Test
	public void testTranscoder() {
		final Person person = new Person();
		person.setName("Daniel Li");
		person.setAge(18);
		
		PerformanceMonitor.start("XMemcachedClientTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				defaultTranscoder.decode(defaultTranscoder.encode(person));
			}
			PerformanceMonitor.mark("原生序列化" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				serializingTranscoder.decode(serializingTranscoder.encode(person));
			}
			PerformanceMonitor.mark("protostuff" + i);
		}
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
//	@Test
	public void testHash() {
		for (int i = 0; i < 10; i++) {
			xMemcachedClient.set(String.valueOf(i), 1000, String.valueOf(i));
		}
	}

//	@Test
	public void testBase() {
		xMemcachedClient.set("name", 10000, "Daniel Li");
		xMemcachedClient.set("age", 10000, "18");
		xMemcachedClient.set("birthday", 10000, new DateTime().toDate());
		
		String name = xMemcachedClient.get("name");
		LOGGER.info(name);
		
		Map<String, ? extends Object> maps = xMemcachedClient.get(Arrays.asList("name", "age"));
		for (Map.Entry<String, ? extends Object> entry : maps.entrySet()) {
			LOGGER.info(entry.getKey() + ":" + entry.getValue());
		}
		
		xMemcachedClient.delete("name");
		long age = xMemcachedClient.incr("age", 5L);
		age = xMemcachedClient.decr("age", 5L);
		LOGGER.info(Long.toString(age));
	}
	
//	@Test
	public void testSpringCache() {
		System.out.println("addUser");
		cacheService.addUser(15, "Daniel Li");
		System.out.println("getUserNameById");
		cacheService.getUserNameById(15);
		System.out.println("deleteUser");
		cacheService.deleteUser(15);
		System.out.println("getUserNameById");
		cacheService.getUserNameById(15);
		System.out.println("getUserNameById");
		cacheService.getUserNameById(15);
	}
	
	/**
	 * 循环次数太短不明显。
	 */
	@Test
	public void test() {
		final Person person = new Person();
		person.setName("Daniel Li");
		person.setAge(18);
		
		PerformanceMonitor.start("XMemcachedClientTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				xMemcachedClient.set("person", 1000, person);
				xMemcachedClient.get("person");
				xMemcachedClient.delete("person");
			}
			PerformanceMonitor.mark("原生序列化" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				xMemcachedClient.set("person", 1000, rpcProtostuffSerializer.serialize(person));
				rpcProtostuffDeserializer.deserialize((byte[]) xMemcachedClient.get("person"), Person.class);
				xMemcachedClient.delete("person");
			}
			PerformanceMonitor.mark("protostuff" + i);
		}
		
		xMemcachedClient.setTranscoder(serializingTranscoder);
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 100000; j++) {
				xMemcachedClient.set("person", 1000, person);
				xMemcachedClient.get("person");
				xMemcachedClient.delete("person");
			}
			PerformanceMonitor.mark("内置protostuff" + i);
		}
		
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	

}