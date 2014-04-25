package org.danielli.xultimate.shard.mybatis.biz.impl;

import java.util.List;

import javax.annotation.Resource;

import net.rubyeye.xmemcached.MemcachedClient;

import org.danielli.xultimate.context.format.FormatterUtils;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.MemcachedClientMutex;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedReturnedCallback;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.XMemcachedTemplate;
import org.danielli.xultimate.core.json.JSONTemplate;
import org.danielli.xultimate.shard.mybatis.biz.VirtualSocketBindRecordBiz;
import org.danielli.xultimate.shard.mybatis.dao.VirtualSocketBindRecordDAO;
import org.danielli.xultimate.shard.po.VirtualSocketBindRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("virtualSocketBindRecordBizImpl")
public class VirtualSocketBindRecordBizImpl implements VirtualSocketBindRecordBiz {

	@Resource(name = "virtualSocketBindRecordDAO")
	private VirtualSocketBindRecordDAO virtualSocketBindRecordDAO;
	
	@Resource(name = "xMemcachedTemplate")
	private XMemcachedTemplate xMemcachedTemplate;
	
	@Resource(name = "memcachedClientMutex")
	private MemcachedClientMutex memcachedClientMutex;
	
	@Resource(name = "jsonTemplate")
	private JSONTemplate jsonTemplate;
	
	@Value("${memcached.expireSeconds}")
	private Integer expireSeconds;	
	
	@Override
	public List<VirtualSocketBindRecord> findByVirtualTableIntervalIdList(final List<Long> virtualTableIntervalIdList) {
		return xMemcachedTemplate.execute(new XMemcachedReturnedCallback<List<VirtualSocketBindRecord>>() {
			@Override
			public List<VirtualSocketBindRecord> doInXMemcached(MemcachedClient memcachedClient) throws Exception {
				String virtualTableIntervalIdListKey = FormatterUtils.format("VirtualSocketBindRecord:virtualTableIntervalIdList:{0}", jsonTemplate.writeValueAsString(virtualTableIntervalIdList));
				List<VirtualSocketBindRecord> virtualSocketBindRecordList = memcachedClient.get(virtualTableIntervalIdListKey);
				if (virtualSocketBindRecordList == null) {
					String virtualTableIntervalIdListKeyLock = FormatterUtils.format("{0}.lock", virtualTableIntervalIdListKey);
					if (memcachedClientMutex.tryLock(memcachedClient, virtualTableIntervalIdListKeyLock)) {
						try {
							virtualSocketBindRecordList = virtualSocketBindRecordDAO.findByVirtualTableIntervalIdList(virtualTableIntervalIdList);
							memcachedClient.set(virtualTableIntervalIdListKey, expireSeconds, virtualSocketBindRecordList);
						} finally {
							memcachedClientMutex.unlock(memcachedClient, virtualTableIntervalIdListKeyLock);
						}
					} else {
						Thread.sleep(500);
						return doInXMemcached(memcachedClient);
					}
				}
				return virtualSocketBindRecordList;
			}
		});
	}

}
