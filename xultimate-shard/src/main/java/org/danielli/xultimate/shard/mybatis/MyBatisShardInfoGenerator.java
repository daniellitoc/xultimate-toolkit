package org.danielli.xultimate.shard.mybatis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.danielli.xultimate.shard.ShardInfoGenerator;
import org.danielli.xultimate.shard.dto.ShardInfo;
import org.danielli.xultimate.shard.mybatis.biz.PartitionedTableIntervalBiz;
import org.danielli.xultimate.shard.mybatis.biz.VirtualTableIntervalBiz;
import org.danielli.xultimate.shard.po.PartitionedTableInterval;
import org.danielli.xultimate.shard.po.VirtualSocketBindRecord;
import org.danielli.xultimate.shard.po.VirtualTableInterval;
import org.danielli.xultimate.util.ArrayUtils;
import org.danielli.xultimate.util.collections.CollectionUtils;
import org.springframework.stereotype.Service;

@Service("myBatisShardInfoGenerator")
public class MyBatisShardInfoGenerator implements ShardInfoGenerator {
	
	@Resource(name = "partitionedTableIntervalBizImpl")
	private PartitionedTableIntervalBiz partitionedTableIntervalBiz;
	
	@Resource(name = "virtualTableIntervalBizImpl")
	private VirtualTableIntervalBiz virtualTableIntervalBiz;
	
	private VirtualTableInterval findVirtualTableIntervalId(String virtualDatabaseName, String virtualTableName, Long intervalValue) {
		List<VirtualTableInterval> virtualTableIntervalList = virtualTableIntervalBiz.findByVirtualDatabaseNameAndVirtualTableName(virtualDatabaseName, virtualTableName);
		for (VirtualTableInterval virtualTableInterval : virtualTableIntervalList) {
			if (!virtualTableInterval.betweenStartIntervalAndEndInterval(intervalValue)) {
				continue;
			}
			if (!virtualTableInterval.getAvailable()) {
				virtualTableIntervalBiz.updateAvailableById(virtualTableInterval.getId(), true);
			}
			return virtualTableInterval;
		}
		return null;
	}
	
	@Override
	public ShardInfo createShardInfo(String virtualDatabaseName, String virtualTableName, Long intervalValue) {
		VirtualTableInterval virtualTableInterval  = findVirtualTableIntervalId(virtualDatabaseName, virtualTableName, intervalValue);
		if (virtualTableInterval == null) return null;
		List<Map<String, Object>> partitionedTableIntervalInfoList = partitionedTableIntervalBiz.findPartitionedTableIntervalInfosByvirtualTableIntervalIdList(Arrays.asList(virtualTableInterval.getId()));
		
		for (Map<String, Object> partitionedTableIntervalInfo : partitionedTableIntervalInfoList) {
			VirtualSocketBindRecord virtualSocketBindRecord = new VirtualSocketBindRecord();
			virtualSocketBindRecord.setHashValuesJson((String) partitionedTableIntervalInfo.get("virtualSocketBindRecordHashValuesJson"));
			if (!ArrayUtils.contains(virtualSocketBindRecord.getHashValues(), (int) (intervalValue % virtualTableInterval.getHashValuesCount()))) {
				continue;
			}
			PartitionedTableInterval partitionedTableInterval = new PartitionedTableInterval();
			partitionedTableInterval.setStartInterval((Long) partitionedTableIntervalInfo.get("partitionedTableStartInterval"));
			partitionedTableInterval.setEndInterval((Long) partitionedTableIntervalInfo.get("partitionedTableEndInterval"));
			if (!partitionedTableInterval.betweenStartIntervalAndEndInterval(intervalValue)) {
				continue;
			}
			ShardInfo shardInfo = new ShardInfo();
			shardInfo.setPartitionedTableShardId((Long) partitionedTableIntervalInfo.get("partitionedTableShardId"));
			shardInfo.setVirtualSocketAddress((String) partitionedTableIntervalInfo.get("virtualSocketAddress"));
			return shardInfo;
		}
		return null;
	}

	@Override
	public Collection<ShardInfo> createShardInfos(String virtualDatabaseName, String virtualTableName) {
		Set<ShardInfo> shardInfoSet = new HashSet<>();
		List<VirtualTableInterval> virtualTableIntervalList = virtualTableIntervalBiz.findByVirtualDatabaseNameAndVirtualTableName(virtualDatabaseName, virtualTableName);
		if (CollectionUtils.isEmpty(virtualTableIntervalList)) {
			return shardInfoSet;
		}
		List<Long> virtualTableIntervalIdList = new ArrayList<>();
		for (VirtualTableInterval virtualTableInterval : virtualTableIntervalList) {
			if (virtualTableInterval.getAvailable()) {
				virtualTableIntervalIdList.add(virtualTableInterval.getId());
			}
		}
		List<Map<String, Object>> partitionedTableIntervalInfoList = partitionedTableIntervalBiz.findPartitionedTableIntervalInfosByvirtualTableIntervalIdList(virtualTableIntervalIdList);
		
		for (Map<String, Object> partitionedTableIntervalInfo : partitionedTableIntervalInfoList) {
			ShardInfo shardInfo = new ShardInfo();
			shardInfo.setPartitionedTableShardId((Long) partitionedTableIntervalInfo.get("partitionedTableShardId"));
			shardInfo.setVirtualSocketAddress((String) partitionedTableIntervalInfo.get("virtualSocketAddress"));
			shardInfoSet.add(shardInfo);
		}
		return shardInfoSet;
	}

}
