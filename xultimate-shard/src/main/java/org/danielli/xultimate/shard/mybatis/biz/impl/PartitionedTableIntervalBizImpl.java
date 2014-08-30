package org.danielli.xultimate.shard.mybatis.biz.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.danielli.xultimate.context.format.FormatterUtils;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedClient;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedClientCallback;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.MemcachedLockFactory;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.MemcachedLockFactory.MemcachedLock;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.XMemcachedClientTemplate;
import org.danielli.xultimate.core.json.JSONTemplate;
import org.danielli.xultimate.shard.mybatis.biz.PartitionedTableIntervalBiz;
import org.danielli.xultimate.shard.mybatis.dao.PartitionedTableIntervalDAO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("partitionedTableIntervalBizImpl")
public class PartitionedTableIntervalBizImpl implements PartitionedTableIntervalBiz, InitializingBean {

	@Resource(name = "partitionedTableIntervalDAO")
	private PartitionedTableIntervalDAO partitionedTableIntervalDAO;
	
	@Resource(name = "xMemcachedClientTemplate")
	private XMemcachedClientTemplate xMemcachedClientTemplate;
	
	@Resource(name = "memcachedLockFactory")
	private MemcachedLockFactory memcachedLockFactory;
	
	@Resource(name = "jsonTemplate")
	private JSONTemplate jsonTemplate;
	
	@Value("${memcached.expireSeconds}")
	private Integer expireSeconds;
	
	private MemcachedLock memcachedLock;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		memcachedLock = memcachedLockFactory.getLock(10);
	}
	
	@Override
	public List<Map<String, Object>> findInfosByVirtualTableIdAndVirtualSocketIdSet(final Long virtualTableId, final Set<Long> virtualSocketIdSet) {
		return xMemcachedClientTemplate.execute(new XMemcachedClientCallback<List<Map<String, Object>>>() {
			@Override
			public List<Map<String, Object>> doInXMemcached(XMemcachedClient xMemcachedClient) throws Exception {
				String partitionedTableIntervalInfosKey = FormatterUtils.format("PartitionedTableInterval:virtualTableId:{0}:virtualSocketIdSet:{1}", virtualTableId, jsonTemplate.writeValueAsString(virtualSocketIdSet));
				List<Map<String, Object>> partitionedTableIntervalInfos = xMemcachedClient.get(partitionedTableIntervalInfosKey);
				if (partitionedTableIntervalInfos == null) {
					String partitionedTableIntervalInfosKeyLock = FormatterUtils.format("{0}.lock", partitionedTableIntervalInfosKey);
					if (memcachedLock.tryLock(partitionedTableIntervalInfosKeyLock)) {
						try {
							partitionedTableIntervalInfos = partitionedTableIntervalDAO.findInfosByVirtualTableIdAndVirtualSocketIdSet(virtualTableId, virtualSocketIdSet);
							xMemcachedClient.set(partitionedTableIntervalInfosKey, expireSeconds, partitionedTableIntervalInfos);
						} finally {
							memcachedLock.unlock(partitionedTableIntervalInfosKeyLock);
						}
					} else {
						Thread.sleep(500);
						return doInXMemcached(xMemcachedClient);
					}
				}
				return partitionedTableIntervalInfos;
			}
		});
	}
}
