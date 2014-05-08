package org.danielli.xultimate.shard.mybatis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
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
	
	private VirtualTableInterval findVirtualTableIntervalId(List<VirtualTableInterval> virtualTableIntervalList, Long intervalValue) {
		for (VirtualTableInterval virtualTableInterval : virtualTableIntervalList) {
			if (!virtualTableInterval.betweenStartIntervalAndEndInterval(intervalValue)) {
				continue;
			}
			if (!virtualTableInterval.getAvailable()) {
				virtualTableIntervalBiz.updateAvailableById(virtualTableInterval.getId(), true);
				virtualTableInterval.setAvailable(true);
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
	
	private ShardInfo createShardInfo(List<VirtualTableInterval> virtualTableIntervalList, Long intervalValue) {
		VirtualTableInterval virtualTableInterval  = findVirtualTableIntervalId(virtualTableIntervalList, intervalValue);
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
	public ShardInfo createShardInfo(String virtualDatabaseName, String virtualTableName, Long intervalValue) {
		List<VirtualTableInterval> virtualTableIntervalList = virtualTableIntervalBiz.findByVirtualDatabaseNameAndVirtualTableName(virtualDatabaseName, virtualTableName);
		return createShardInfo(virtualTableIntervalList, intervalValue);
	}
	
	
	
	@Override
	public Map<ShardInfo, Collection<Long>> createShardInfosByIntervalValue(String virtualDatabaseName, String virtualTableName, Collection<Long> intervalValues) {
		List<VirtualTableInterval> virtualTableIntervalList = virtualTableIntervalBiz.findByVirtualDatabaseNameAndVirtualTableName(virtualDatabaseName, virtualTableName);
		
		Map<ShardInfo, Collection<Long>> shardInfoMap = new HashMap<>();
		for (Long intervalValue : intervalValues) {
			ShardInfo shardInfo = createShardInfo(virtualTableIntervalList, intervalValue);
			if (shardInfo != null) {
				Collection<Long> resultValue = shardInfoMap.get(shardInfo);
				if (resultValue == null) {
					resultValue = new ArrayList<>();
					shardInfoMap.put(shardInfo, resultValue);
				}
				resultValue.add(intervalValue);
			}
		}
		return shardInfoMap;
	}

	private List<Map<String, Object>> findPartitionedTableIntervalInfoList(String virtualDatabaseName, String virtualTableName) {
		List<VirtualTableInterval> virtualTableIntervalList = virtualTableIntervalBiz.findByVirtualDatabaseNameAndVirtualTableName(virtualDatabaseName, virtualTableName);
		if (CollectionUtils.isEmpty(virtualTableIntervalList)) {
			return null;
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
			return null;
		}
		
		List<VirtualSocketBindRecord> virtualSocketBindRecordList = virtualSocketBindRecordBiz.findByVirtualTableIntervalIdList(virtualTableIntervalIdList);
		Set<Long> virtualSocketIdSet = findVirtualSocketIdSet(virtualSocketBindRecordList);
		if (CollectionUtils.isEmpty(virtualSocketIdSet))  {
			return null;
		}
		
		return partitionedTableIntervalBiz.findInfosByVirtualTableIdAndVirtualSocketIdSet(virtualTableId, virtualSocketIdSet);
	}
	
	@Override
	public Collection<ShardInfo> createShardInfos(String virtualDatabaseName, String virtualTableName) {
		List<Map<String, Object>> partitionedTableIntervalInfoList = findPartitionedTableIntervalInfoList(virtualDatabaseName, virtualTableName);
		if (CollectionUtils.isEmpty(partitionedTableIntervalInfoList)) {
			return new HashSet<>();
		} else {
			Set<ShardInfo> shardInfoSet = new HashSet<>();
			for (Map<String, Object> partitionedTableIntervalInfo : partitionedTableIntervalInfoList) {
				ShardInfo shardInfo = new ShardInfo();
				shardInfo.setPartitionedTableShardId((Long) partitionedTableIntervalInfo.get("partitionedTableShardId"));
				shardInfo.setVirtualSocketAddress((String) partitionedTableIntervalInfo.get("virtualSocketAddress"));
				shardInfoSet.add(shardInfo);
			}
			return shardInfoSet;
		}
	}

}
