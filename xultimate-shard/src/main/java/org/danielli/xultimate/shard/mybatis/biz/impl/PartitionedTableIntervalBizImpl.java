package org.danielli.xultimate.shard.mybatis.biz.impl;

import java.util.List;
import java.util.Map;

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
	public List<Map<String, Object>> findPartitionedTableIntervalInfosByvirtualTableIntervalIdList(final List<Long> virtualTableIntervalIdList) {
		return xMemcachedTemplate.execute(new XMemcachedReturnedCallback<List<Map<String, Object>>>() {
			@Override
			public List<Map<String, Object>> doInXMemcached(MemcachedClient memcachedClient) throws Exception {
				String virtualTableIntervalIdListKey = FormatterUtils.format("PartitionedTableInterval:virtualTableIntervalIdList:{0}", jsonTemplate.writeValueAsString(virtualTableIntervalIdList));
				List<Map<String, Object>> partitionedTableIntervalInfos = memcachedClient.get(virtualTableIntervalIdListKey);
				if (partitionedTableIntervalInfos == null) {
					String virtualTableIntervalIdListKeyLock = FormatterUtils.format("{0}.lock", virtualTableIntervalIdListKey);
					if (memcachedClientMutex.tryLock(memcachedClient, virtualTableIntervalIdListKeyLock)) {
						try {
							partitionedTableIntervalInfos = partitionedTableIntervalDAO.findPartitionedTableIntervalInfosByvirtualTableIntervalIdList(virtualTableIntervalIdList);
							memcachedClient.set(virtualTableIntervalIdListKey, expireSeconds, partitionedTableIntervalInfos);
						} finally {
							memcachedClientMutex.unlock(memcachedClient, virtualTableIntervalIdListKeyLock);
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
