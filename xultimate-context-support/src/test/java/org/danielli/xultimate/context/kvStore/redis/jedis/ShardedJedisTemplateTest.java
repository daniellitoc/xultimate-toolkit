package org.danielli.xultimate.context.kvStore.redis.jedis;

import javax.annotation.Resource;

import org.danielli.xultimate.context.kvStore.redis.jedis.support.ShardedJedisTemplate;
import org.danielli.xultimate.core.json.ValueType;
import org.danielli.xultimate.core.json.fastjson.FastJSONTemplate;
import org.danielli.xultimate.core.serializer.Deserializer;
import org.danielli.xultimate.core.serializer.Serializer;
import org.danielli.xultimate.core.serializer.java.StringSerializer;
import org.danielli.xultimate.core.serializer.protostuff.MainProtobufSerializer;
import org.danielli.xultimate.core.serializer.protostuff.RpcProtobufSerializer;
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
	
	@Resource
	private Serializer protobufSerializer;
	
	@Resource
	private Deserializer protobufDeserializer;
	
	@Resource
	private MainProtobufSerializer mainProtobufSerializer;
	
	@Resource
	private RpcProtobufSerializer rpcProtobufSerializer;
	
	@Resource
	private StringSerializer stringSerializer;
	
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
						byte[] key = protobufSerializer.serialize("person");
						shardedJedis.set(key, protobufSerializer.serialize(person));
						protobufDeserializer.deserialize(shardedJedis.get(key), Person.class);
						shardedJedis.getShard(shardedJedis.get(key)).del(shardedJedis.get(key));
					}
				});
			}
			PerformanceMonitor.mark("protobuf序列化对象" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10000; j++) {
				shardedJedisTemplate.execute(new ShardedJedisCallback() {
					
					@Override
					public void doInShardedJedis(ShardedJedis shardedJedis) {
						byte[] key = rpcProtobufSerializer.serialize("person");
						shardedJedis.set(key, rpcProtobufSerializer.serialize(person));
						rpcProtobufSerializer.deserialize(shardedJedis.get(key), Person.class);
						shardedJedis.getShard(shardedJedis.get(key)).del(shardedJedis.get(key));
					}
				});
			}
			PerformanceMonitor.mark("rpcProtobuf序列化对象" + i);
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
			PerformanceMonitor.mark("JSON序列化对象" + i);
		}
		
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
	
//	@Test
	public void testSerializable () {
		PerformanceMonitor.start("JedisTemplateTest");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10000; j++) {
				shardedJedisTemplate.execute(new ShardedJedisCallback() {
					
					@Override
					public void doInShardedJedis(ShardedJedis shardedJedis) {
						String key = "key";
						shardedJedis.set(key, "value");
						shardedJedis.get(key);
						shardedJedis.getShard(key).del(key);
					}
				});
			}
			PerformanceMonitor.mark("原生序列化" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10000; j++) {
				shardedJedisTemplate.execute(new ShardedJedisCallback() {
					
					@Override
					public void doInShardedJedis(ShardedJedis shardedJedis) {
						byte[] key = stringSerializer.serialize("key");
						shardedJedis.set(key, stringSerializer.serialize("value"));
						stringSerializer.deserialize(shardedJedis.get(key), String.class);
						shardedJedis.getShard(shardedJedis.get(key)).del(shardedJedis.get(key));
					}
				});
			}
			PerformanceMonitor.mark("String Serializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10000; j++) {
				shardedJedisTemplate.execute(new ShardedJedisCallback() {
					
					@Override
					public void doInShardedJedis(ShardedJedis shardedJedis) {
						byte[] key = rpcProtobufSerializer.serialize("key");
						shardedJedis.set(key, rpcProtobufSerializer.serialize("value"));
						rpcProtobufSerializer.deserialize(shardedJedis.get(key), String.class);
						shardedJedis.getShard(shardedJedis.get(key)).del(shardedJedis.get(key));
					}
				});
			}
			PerformanceMonitor.mark("PRC Protostuff" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10000; j++) {
				shardedJedisTemplate.execute(new ShardedJedisCallback() {
					
					@Override
					public void doInShardedJedis(ShardedJedis shardedJedis) {
						byte[] key = mainProtobufSerializer.serialize("key");
						shardedJedis.set(key, mainProtobufSerializer.serialize("value"));
						mainProtobufSerializer.deserialize(shardedJedis.get(key), String.class);
						shardedJedis.getShard(shardedJedis.get(key)).del(shardedJedis.get(key));
					}
				});
			}
			PerformanceMonitor.mark("Main Protobuf Serializer" + i);
		}
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 10000; j++) {
				shardedJedisTemplate.execute(new ShardedJedisCallback() {
					
					@Override
					public void doInShardedJedis(ShardedJedis shardedJedis) {
						byte[] key = protobufSerializer.serialize("key");
						shardedJedis.set(key, protobufSerializer.serialize("value"));
						protobufDeserializer.deserialize(shardedJedis.get(key), String.class);
						shardedJedis.getShard(shardedJedis.get(key)).del(shardedJedis.get(key));
					}
				});
			}
			PerformanceMonitor.mark("Protobuf Factory" + i);
		}
		
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
}
