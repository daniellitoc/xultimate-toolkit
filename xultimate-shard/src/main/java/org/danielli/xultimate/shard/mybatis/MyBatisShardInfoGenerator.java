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
import org.danielli.xultimate.shard.mybatis.biz.VirtualSocketBindRecordBiz;
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
	
	@Resource(name = "virtualSocketBindRecordBizImpl")
	private VirtualSocketBindRecordBiz virtualSocketBindRecordBiz;
	
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
	
	private Set<Long> findVirtualSocketIdSet(List<VirtualSocketBindRecord> virtualSocketBindRecordList, Long intervalValue, Integer hashValuesCount) {
		Set<Long> virtualSocketIdSet = new HashSet<>();
		for (VirtualSocketBindRecord virtualSocketBindRecord : virtualSocketBindRecordList) {
			if (!ArrayUtils.contains(virtualSocketBindRecord.getHashValues(), (int) (intervalValue % hashValuesCount))) {
				continue;
			}
			virtualSocketIdSet.add(virtualSocketBindRecord.getVirtualSocketId());
			break;
		}
		return virtualSocketIdSet;
	}
	
	private Set<Long> findVirtualSocketIdSet(List<VirtualSocketBindRecord> virtualSocketBindRecordList) {
		Set<Long> virtualSocketIdSet = new HashSet<>();
		for (VirtualSocketBindRecord virtualSocketBindRecord : virtualSocketBindRecordList) {
			virtualSocketIdSet.add(virtualSocketBindRecord.getVirtualSocketId());
		}
		return virtualSocketIdSet;
	}
	
	@Override
	public ShardInfo createShardInfo(String virtualDatabaseName, String virtualTableName, Long intervalValue) {
		VirtualTableInterval virtualTableInterval  = findVirtualTableIntervalId(virtualDatabaseName, virtualTableName, intervalValue);
		if (virtualTableInterval == null) return null;
		
		List<VirtualSocketBindRecord> virtualSocketBindRecordList = virtualSocketBindRecordBiz.findByVirtualTableIntervalIdList(Arrays.asList(virtualTableInterval.getId()));
		Set<Long> virtualSocketIdSet = findVirtualSocketIdSet(virtualSocketBindRecordList, intervalValue, virtualTableInterval.getHashValuesCount());
		if (CollectionUtils.isEmpty(virtualSocketIdSet)) return null;
		
		List<Map<String, Object>> partitionedTableIntervalInfoList = partitionedTableIntervalBiz.findInfosByVirtualTableIdAndVirtualSocketIdSet(virtualTableInterval.getVirtualTableId(), virtualSocketIdSet);
		
		for (Map<String, Object> partitionedTableIntervalInfo : partitionedTableIntervalInfoList) {
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
		Long virtualTableId = null;
		for (VirtualTableInterval virtualTableInterval : virtualTableIntervalList) {
			if (virtualTableInterval.getAvailable()) {
				virtualTableIntervalIdList.add(virtualTableInterval.getId());
				virtualTableId = virtualTableInterval.getVirtualTableId();
			}
		}
		if (virtualTableId == null) {
			return shardInfoSet;
		}
		
		List<VirtualSocketBindRecord> virtualSocketBindRecordList = virtualSocketBindRecordBiz.findByVirtualTableIntervalIdList(virtualTableIntervalIdList);
		Set<Long> virtualSocketIdSet = findVirtualSocketIdSet(virtualSocketBindRecordList);
		if (CollectionUtils.isEmpty(virtualSocketIdSet))  {
			return shardInfoSet;
		}
		
		List<Map<String, Object>> partitionedTableIntervalInfoList = partitionedTableIntervalBiz.findInfosByVirtualTableIdAndVirtualSocketIdSet(virtualTableId, virtualSocketIdSet);
		
		for (Map<String, Object> partitionedTableIntervalInfo : partitionedTableIntervalInfoList) {
			ShardInfo shardInfo = new ShardInfo();
			shardInfo.setPartitionedTableShardId((Long) partitionedTableIntervalInfo.get("partitionedTableShardId"));
			shardInfo.setVirtualSocketAddress((String) partitionedTableIntervalInfo.get("virtualSocketAddress"));
			shardInfoSet.add(shardInfo);
		}
		return shardInfoSet;
	}

}
