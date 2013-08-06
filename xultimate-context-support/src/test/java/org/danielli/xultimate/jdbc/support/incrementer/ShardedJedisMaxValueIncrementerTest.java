package org.danielli.xultimate.jdbc.support.incrementer;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/kvStore/redis/applicationContext-service-redis.xml" })
public class ShardedJedisMaxValueIncrementerTest {

	@Resource(name = "primaryKey1Incrementer1")
	private ShardedJedisMaxValueIncrementer primaryKey1Incrementer1;
	
	@Resource(name = "primaryKey1Incrementer2")
	private ShardedJedisMaxValueIncrementer primaryKey1Incrementer2;
	
	private final Logger logger = LoggerFactory.getLogger(ShardedJedisMaxValueIncrementerTest.class);
	
	@Test
	public void test() {
		for (int i = 0; i < 200; i++) {
			logger.info("{}", primaryKey1Incrementer1.nextLongValue());
		}
		
		for (int i = 0; i < 200; i++) {
			logger.info("{}", primaryKey1Incrementer2.nextLongValue());
		}
	}
}
