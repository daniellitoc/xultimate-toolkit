package org.danielli.xultimate.shard;

import java.util.Collection;

import javax.annotation.Resource;

import org.danielli.xultimate.shard.dto.ShardInfo;
import org.danielli.xultimate.util.performance.PerformanceMonitor;
import org.danielli.xultimate.util.time.stopwatch.support.AdvancedStopWatchSummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-service-crypto.xml", "classpath:applicationContext-service-config.xml", "classpath:applicationContext-dao-base.xml", "classpath:primaryKey/applicationContext-dao-primaryKey.xml", "classpath:shard/applicationContext-dao-shard.xml", "classpath:shard/applicationContext-dao-generic.xml", "classpath*:shard/applicationContext-service-*.xml" })
public class ShardInfoGeneratorTest {

	@Resource(name = "myBatisShardInfoGenerator")
	private ShardInfoGenerator shardInfoGenerator;
	
	@Resource(name = "primaryKey1Incrementer")
	private DataFieldMaxValueIncrementer dataFieldMaxValueIncrementer;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShardInfoGeneratorTest.class);
	
	@Test
	public void test() {
		LOGGER.info("分表操作需要: ");
		PerformanceMonitor.start("ShardInfoGeneratorTest");
		while (true) {
			Long id = dataFieldMaxValueIncrementer.nextLongValue();
			if (id.compareTo(150L) >= 0) {
				break;
			}
			ShardInfo shardInfo = shardInfoGenerator.createShardInfo("test", "test_table", id);
			LOGGER.info("\tID:{} 被分配的主机{}, 分片ID{}", id, shardInfo.getVirtualSocketAddress(), shardInfo.getPartitionedTableShardId());
		}
		PerformanceMonitor.mark("分表操作");
		Collection<ShardInfo> shardInfos = shardInfoGenerator.createShardInfos("test", "test_table");
		LOGGER.info("并行查询需要: ");
		for (ShardInfo shardInfo : shardInfos) {
			LOGGER.info("\t主机{}, 分片ID{}", shardInfo.getVirtualSocketAddress(), shardInfo.getPartitionedTableShardId());
		}
		PerformanceMonitor.mark("并行查询操作");
		PerformanceMonitor.stop();
		PerformanceMonitor.summarize(new AdvancedStopWatchSummary(true));
		PerformanceMonitor.remove();
	}
}
