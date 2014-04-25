package org.danielli.xultimate.context.kvStore.redis.jedis;

import javax.annotation.Resource;

import org.danielli.xultimate.context.kvStore.redis.jedis.support.ShardedJedisTemplate;
import org.danielli.xultimate.core.json.ValueType;
import org.danielli.xultimate.core.json.fastjson.FastJSONTemplate;
import org.danielli.xultimate.core.serializer.support.BaseTypeDeserializer;
import org.danielli.xultimate.core.serializer.support.BaseTypeSerializer;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.ShardedJedis;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/kvStore/redis/applicationContext-service-redis.xml", "classpath:/kvStore/applicationContext-service-serializer.xml"})
public class ShardedJedisTemplateTest {
	
	@Resource(name = "shardedJedisTemplate")
	private ShardedJedisTemplate shardedJedisTemplate;
	
	@Resource(name = "rpcProtostuffSerializerProxy")
	private BaseTypeSerializer rpcProtostuffSerializerProxy;
	
	@Resource(name = "rpcProtostuffDeserializerProxy")
	private BaseTypeDeserializer rpcProtostuffDeserializerProxy;
	
	@Resource
	private FastJSONTemplate fastJsonTemplate;
	
	@Test
	public void test() {
		final Person person = new Person();
		person.setName("Daniel Li");
		person.setAge(18);
		
		PerformanceMonitor.start("JedisTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10000; j++) {
				shardedJedisTemplate.execute(new ShardedJedisCallback() {
					
					@Override
					public void doInShardedJedis(ShardedJedis shardedJedis) {
						byte[] key = rpcProtostuffSerializerProxy.serializeString("person");
						shardedJedis.set(key, rpcProtostuffSerializerProxy.serialize(person));
						rpcProtostuffDeserializerProxy.deserialize(shardedJedis.get(key), Person.class);
						shardedJedis.getShard(shardedJedis.get(key)).del(shardedJedis.get(key));
					}
				});
			}
			PerformanceMonitor.mark("protostuff序列化对象" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10000; j++) {
				shardedJedisTemplate.execute(new ShardedJedisCallback() {
					
					@Override
					public void doInShardedJedis(ShardedJedis shardedJedis) {
						shardedJedis.set("person", fastJsonTemplate.writeValueAsString(person));
						fastJsonTemplate.readValue(shardedJedis.get("person"), new ValueType<Person>() { });
						shardedJedis.getShard("person").del("person");
					}
				});
			}
			PerformanceMonitor.mark("原生 + JSON序列化对象" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10000; j++) {
				shardedJedisTemplate.execute(new ShardedJedisCallback() {
					
					@Override
					public void doInShardedJedis(ShardedJedis shardedJedis) {
						byte[] key = rpcProtostuffSerializerProxy.serializeString("person");
						shardedJedis.set(key, rpcProtostuffSerializerProxy.serializeString(fastJsonTemplate.writeValueAsString(person)));
						fastJsonTemplate.readValue(rpcProtostuffDeserializerProxy.deserializeString(shardedJedis.get(key)), new ValueType<Person>() { });
						shardedJedis.getShard(key).del(key);
					}
				});
			}
			PerformanceMonitor.mark("String Serializer + JSON序列化对象" + i);
		}
		
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
}
