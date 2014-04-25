package org.danielli.xultimate.shard.mybatis.biz.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.rubyeye.xmemcached.MemcachedClient;

import org.danielli.xultimate.context.format.FormatterUtils;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.MemcachedClientMutex;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedReturnedCallback;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.XMemcachedTemplate;
import org.danielli.xultimate.core.json.JSONTemplate;
import org.danielli.xultimate.shard.mybatis.biz.PartitionedTableIntervalBiz;
import org.danielli.xultimate.shard.mybatis.dao.PartitionedTableIntervalDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("partitionedTableIntervalBizImpl")
public class PartitionedTableIntervalBizImpl implements PartitionedTableIntervalBiz {

	@Resource(name = "partitionedTableIntervalDAO")
	private PartitionedTableIntervalDAO partitionedTableIntervalDAO;
	
	@Resource(name = "xMemcachedTemplate")
	private XMemcachedTemplate xMemcachedTemplate;
	
	@Resource(name = "memcachedClientMutex")
	private MemcachedClientMutex memcachedClientMutex;
	
	@Resource(name = "jsonTemplate")
	private JSONTemplate jsonTemplate;
	
	@Value("${memcached.expireSeconds}")
	private Integer expireSeconds;
	
	@Override
	public List<Map<String, Object>> findInfosByVirtualTableIdAndVirtualSocketIdSet(final Long virtualTableId, final Set<Long> virtualSocketIdSet) {
		return xMemcachedTemplate.execute(new XMemcachedReturnedCallback<List<Map<String, Object>>>() {
			@Override
			public List<Map<String, Object>> doInXMemcached(MemcachedClient memcachedClient) throws Exception {
				String partitionedTableIntervalInfosKey = FormatterUtils.format("PartitionedTableInterval:virtualTableId:{0}:virtualSocketIdSet:{1}", virtualTableId, jsonTemplate.writeValueAsString(virtualSocketIdSet));
				List<Map<String, Object>> partitionedTableIntervalInfos = memcachedClient.get(partitionedTableIntervalInfosKey);
				if (partitionedTableIntervalInfos == null) {
					String partitionedTableIntervalInfosKeyLock = FormatterUtils.format("{0}.lock", partitionedTableIntervalInfosKey);
					if (memcachedClientMutex.tryLock(memcachedClient, partitionedTableIntervalInfosKeyLock)) {
						try {
							partitionedTableIntervalInfos = partitionedTableIntervalDAO.findInfosByVirtualTableIdAndVirtualSocketIdSet(virtualTableId, virtualSocketIdSet);
							memcachedClient.set(partitionedTableIntervalInfosKey, expireSeconds, partitionedTableIntervalInfos);
						} finally {
							memcachedClientMutex.unlock(memcachedClient, partitionedTableIntervalInfosKeyLock);
						}
					} else {
						Thread.sleep(500);
						return doInXMemcached(memcachedClient);
					}
				}
				return partitionedTableIntervalInfos;
			}
		});
	}
}
